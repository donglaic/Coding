import java.util.concurrent.*;


public class InnerFutureDemo {
	public static void main(String[] args) throws InterruptedException,ExecutionException {
		FutureTask<String> future = new FutureTask<String>(new RealData("a"));
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.submit(future);
		System.out.println("request done");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e){}
		System.out.println("data="+future.get());
		executor.shutdown();
	}
}

class RealData implements Callable<String> {
	private String para;
	public RealData(String para){
		this.para = para;
	}
	@Override
	public String call() throws Exception {
		StringBuffer sb = new StringBuffer();
		for (int i=0;i<10;i++){
			sb.append(para);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e){}
		}
		return sb.toString();
	}
}

