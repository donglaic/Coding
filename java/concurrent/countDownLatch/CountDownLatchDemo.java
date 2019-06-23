import java.util.concurrent.*;
import java.util.Random;

public class CountDownLatchDemo implements Runnable {
	static final CountDownLatch end = new CountDownLatch(10);
	static final CountDownLatchDemo demo = new CountDownLatchDemo();

	@Override
	public void run() {
		try {
			//check task
			Thread.sleep(new Random().nextInt(10)*1000);
			System.out.println("check complete");
			end.countDown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		ExecutorService exec = Executors.newFixedThreadPool(10);
		for (int i=0;i<10;i++){
			exec.submit(demo);
		}
		// wait check
		end.await();
		// submit rock
		System.out.println("Fire");
		exec.shutdown();
	}
}
