import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.io.*;
import java.net.*;

public class HeavySocketClient {
	private static ExecutorService tp = Executors.newCachedThreadPool();
	private static final int sleep_time = 1000*1000*1000;

	public static class EchoClient implements Runnable {
		public void run(){
			Socket client = null;
			PrintWriter writer = null;
			BufferedReader reader = null;

			try {
				client = new Socket();
				client.connect(new InetSocketAddress("localhost",8000));
				writer = new PrintWriter(client.getOutputStream(),true);
				writer.print("H"); LockSupport.parkNanos(sleep_time);
				writer.print("i"); LockSupport.parkNanos(sleep_time);
				writer.print("i"); LockSupport.parkNanos(sleep_time);
				writer.print("i"); LockSupport.parkNanos(sleep_time);
				writer.print("i"); LockSupport.parkNanos(sleep_time);
				writer.print("i"); LockSupport.parkNanos(sleep_time);
				writer.println();
				writer.flush();

				reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
				System.out.println("from server: "+reader.readLine());
			} catch (UnknownHostException e){
				e.printStackTrace();
			} catch (IOException e){
				e.printStackTrace();
			} finally {
				try {
					if(writer != null) writer.close();
					if(reader != null) reader.close();
					if(client != null) client.close();
				} catch (IOException e){}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		EchoClient ec = new EchoClient();
		for(int i=0;i<10;i++){
			tp.execute(ec);
		}
	}
}
