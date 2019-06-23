import java.util.concurrent.*;
import java.util.Random;
import java.text.*;
import java.util.concurrent.atomic.*;
public class Producer implements Runnable {
	private volatile boolean isRunning = true;

	private BlockingQueue<PCData> queue; // memory cache

	private static AtomicInteger count = new AtomicInteger(); // totalNum atomic op

	private static final int SLEEPTIME = 1000;

	public Producer(BlockingQueue<PCData> queue){
		this.queue = queue;
	}

	public void run(){
		PCData data = null;
		Random r = new Random();

		System.out.println("start producer id= "+Thread.currentThread().getId());
		
		try{
			while(isRunning){
				Thread.sleep(r.nextInt(SLEEPTIME));
				data = new PCData(count.incrementAndGet()); // construct new task data
				System.out.println(data+" is put into queue");
				if (!queue.offer(data,2,TimeUnit.SECONDS)){
					System.err.println("failed to put data: "+data);
				}
			}
		} catch (InterruptedException e){
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}

	public void stop(){
		isRunning = false;
	}
}
