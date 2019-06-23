import java.util.concurrent.*;
public class OddEvenSortDemo {
	static int[] arr = {1,3,5,3,1,2,4,5,623,1};
	static int exchFlag=1;
	static ExecutorService pool = Executors.newFixedThreadPool(4);
	
	static synchronized void setExchFlag(int v){exchFlag=v;}
	static synchronized int getExchFlag(){return exchFlag;}

	public static class OddEvenSortTask implements Runnable {
		int i;
		CountDownLatch latch;
		public OddEvenSortTask(int i,CountDownLatch latch){
			this.i = i;
			this.latch = latch;
		}
		@Override
		public void run(){
			if(arr[i]>arr[i+1]){
				int temp = arr[i];
				arr[i] = arr[i+1];
				arr[i+1] = temp;
				setExchFlag(1);
			}
			latch.countDown();
		}
	}

	public static void pOddEvenSort(int[] arr) throws InterruptedException {
		int start = 0;
		while(getExchFlag()==1 || start==1){
			setExchFlag(0);
			CountDownLatch latch = new CountDownLatch(arr.length/2-(arr.length%2==0?start:0));
			for(int i=start;i<arr.length-1;i+=2){
				pool.submit(new OddEvenSortTask(i,latch));
			}
			latch.await();
			if(start==0)
				start=1;
			else
				start=0;
		}
	}

	public static void main(String[] args) throws InterruptedException {
		pOddEvenSort(arr);
		for (int i=0;i<arr.length;i++){
			System.out.println(arr[i]);
		}
		pool.shutdown();
	}
}
