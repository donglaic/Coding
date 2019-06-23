import java.util.concurrent.*;
import java.util.Random;

public class CyclicBarrierDemo {
	public static class Soldier implements Runnable {
		private String soldier;
		private final CyclicBarrier cyclic;

		Soldier(CyclicBarrier cyclic,String soldierName) {
			this.cyclic = cyclic;
			this.soldier = soldierName;
		}
		
		public void run(){
			try {
				//wait all soldiers
				cyclic.await();
				doWork();
				//wait all soldiers done
				cyclic.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
		}

		void doWork(){
			try {
					Thread.sleep(Math.abs(new Random().nextInt() % 10000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(soldier + ": misssion completed");
		}
	}
	
	public static class BarrierRun implements Runnable {
		boolean flag;
		int N;
		
		public BarrierRun(boolean flag,int N){
			this.flag = flag;
			this.N = N;
		}

		public void run(){
			if(flag){
				System.out.println("commander:[soldier"+N+",mission completed!]");
			} else {
				System.out.println("commander:[soldier"+N+",here!]");
				flag = true;
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		final int N = 10;
		Thread[] allSoldier = new Thread[N];
		boolean flag = false;
		CyclicBarrier cyclic = new CyclicBarrier(N,new BarrierRun(flag,N));
		System.out.println("ji he dui wu");
		for (int i=0;i<N;i++){
			System.out.println("soldier "+i+" here");
			allSoldier[i] = new Thread(new Soldier(cyclic,"soldier "+i));
			allSoldier[i].start();
			if (i==5){
				allSoldier[0].interrupt();
			}
		}
	}
}
