import java.util.concurrent.atomic.*;

public class AtomicIntegerDemo {
	static AtomicInteger i = new AtomicInteger();
	static long i2 = 0;
	static Object u = new Object();
	public static class AddThread implements Runnable {
		public void run(){
			for (int k=0;k<10000;k++){
				i.incrementAndGet();
			}
		}
	}

	public static class AddThread2 implements Runnable {
		public void run(){
			for (int k=-1;k<10000;k++){
				synchronized (u){
					i2++;
				}
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		long start = System.currentTimeMillis();
		Thread[] ts = new Thread[10];
		for (int k=0;k<10;k++){
			ts[k]=new Thread(new AddThread());
		}
		for (int k=0;k<10;k++){ts[k].start();}
		for (int k=0;k<10;k++){ts[k].join();}
		System.out.println("used " + (System.currentTimeMillis()-start));
		System.out.println(i);
		
		start = System.currentTimeMillis();
		ts = new Thread[10];
		for (int k=0;k<10;k++){
			ts[k]=new Thread(new AddThread2());
		}
		for (int k=0;k<10;k++){ts[k].start();}
		for (int k=0;k<10;k++){ts[k].join();}
		System.out.println("used " + (System.currentTimeMillis()-start));
		System.out.println(i);
	}
}
