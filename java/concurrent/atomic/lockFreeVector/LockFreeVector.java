import java.util.concurrent.atomic.*;

public class LockFreeVector<E>{
	final static int N_BUCKET = 30;
	final static int FIRST_BUCKET_SIZE = 8;
	final static int zeroNumFirst = Integer.numberOfLeadingZeros(FIRST_BUCKET_SIZE);
	static boolean debug = true;

	private final AtomicReferenceArray<AtomicReferenceArray<E>> buckets;
	private final AtomicReference<Descriptor<E>> descriptor;

	static class Descriptor<E> {
		public int size;
		volatile WriteDescriptor<E> writeop;
		public Descriptor(int size, WriteDescriptor<E> writeop){
			this.size = size;
			this.writeop = writeop;
		}
		public void completeWrite(){
			WriteDescriptor<E> tmpOp = writeop;
			if (tmpOp!=null){
				tmpOp.dolt();
				writeop = null; //this is safe since all write to writeop use null as r_value
			}
		}
	}

	static class WriteDescriptor<E> {
		public E oldV;
		public E newV;
		public AtomicReferenceArray<E> addr;
		public int addr_ind;

		public WriteDescriptor(AtomicReferenceArray<E> addr, int addr_ind, E oldV, E newV){
			this.addr = addr;
			this.addr_ind = addr_ind;
			this.oldV = oldV;
			this.newV = newV;
		}

		public void dolt(){
			addr.compareAndSet(addr_ind,oldV,newV);
		}
	}

	public LockFreeVector(){
		buckets = new AtomicReferenceArray<AtomicReferenceArray<E>>(N_BUCKET);
		buckets.set(0,new AtomicReferenceArray<E>(FIRST_BUCKET_SIZE));
		descriptor = new AtomicReference<Descriptor<E>>(new Descriptor<E>(0,null));
	}

	public void push_back(E e){
		Descriptor<E> desc;
		Descriptor<E> newd;
		do {
			desc = descriptor.get();
			desc.completeWrite();

			int pos = desc.size + FIRST_BUCKET_SIZE;
			int zeroNumPos = Integer.numberOfLeadingZeros(pos);
			int bucketInd = zeroNumFirst - zeroNumPos;
			if (buckets.get(bucketInd)==null){
				int newLen = 2*buckets.get(bucketInd-1).length();
				if(debug)
					System.out.println("New Length is: " + newLen);
				buckets.compareAndSet(bucketInd,null,new AtomicReferenceArray<E>(newLen));
			}
			int idx = (0x80000000 >>>zeroNumPos)^pos;
			newd = new Descriptor<E>(desc.size+1,new WriteDescriptor<E>(buckets.get(bucketInd),idx,null,e));
		}while(!descriptor.compareAndSet(desc,newd));
		descriptor.get().completeWrite();
	}

	//@Overide
	public E get(int index){
		int pos = index + FIRST_BUCKET_SIZE;
		int zeroNumPos = Integer.numberOfLeadingZeros(pos);
		int bucketInd = zeroNumFirst - zeroNumPos;
		int idx = (0x80000000>>>zeroNumPos)^pos;
		return buckets.get(bucketInd).get(idx);
	}

	public static void main(String[] args) {
		LockFreeVector v = new LockFreeVector();
		for (long i=0;i<10000;i++){
			v.push_back(i);
		}
		System.out.println("last: " + v.get(1000));
	}
}
