import java.util.concurrent.*;
import java.nio.*;
import java.util.*;
import java.io.*;
import java.net.*;
import java.nio.channels.*;
import java.nio.channels.spi.*;
public class AioServer {
	public final static int PORT = 8000;
	private AsynchronousServerSocketChannel server;

	public AioServer() throws IOException {
		server = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(PORT));
	}

	public void start() throws InterruptedException,ExecutionException,TimeoutException {
		System.out.println("Sever listen on "+PORT);
		server.accept(null,new CompletionHandler<AsynchronousSocketChannel,Object>(){
			final ByteBuffer buffer = ByteBuffer.allocate(1024);

			public void completed(AsynchronousSocketChannel result,Object attachment){
				System.out.println(Thread.currentThread().getName());
				Future<Integer> writeResult = null;
				try{
					buffer.clear();
					result.read(buffer).get(100,TimeUnit.SECONDS);
					buffer.flip();
					writeResult=result.write(buffer);
				} catch (InterruptedException | ExecutionException e){
					e.printStackTrace();
				} catch (TimeoutException e){
					e.printStackTrace();
				} finally {
					try {
						server.accept(null,this);
						writeResult.get();
						result.close();
					} catch (Exception e){
						System.out.println(e.toString());
					}
				}
			}

			@Override
			public void failed(Throwable exc,Object attachment){
				System.out.println("failed: "+exc);
			}
		});
	}

	public static void main(String[] main) throws Exception{
		new AioServer().start();
		while(true) Thread.sleep(1000);
	}
}
