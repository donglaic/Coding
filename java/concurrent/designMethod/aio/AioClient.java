import java.util.concurrent.*;
import java.nio.*;
import java.util.*;
import java.io.*;
import java.net.*;
import java.nio.channels.*;
import java.nio.channels.spi.*;

public class AioClient {
	public static void main(String[] args) throws Exception {
		final AsynchronousSocketChannel client = AsynchronousSocketChannel.open();

		client.connect(new InetSocketAddress("localhost",8000),null,new CompletionHandler<Void,Object>(){
				@Override
				public void completed(Void result,Object attachment){
					client.write(ByteBuffer.wrap("Hello".getBytes()),null,new CompletionHandler<Integer,Object>(){
						@Override
						public void completed(Integer result,Object attachment){
							try {
								ByteBuffer buffer = ByteBuffer.allocate(1024);
								client.read(buffer,buffer,new CompletionHandler<Integer,ByteBuffer>(){
									@Override
									public void completed(Integer result,ByteBuffer buffer){
										buffer.flip();
										System.out.println(new String(buffer.array()));
										try{
											client.close();
										}catch(IOException e){
											e.printStackTrace();
										}
									}
									@Override
									public void failed(Throwable exc,ByteBuffer attachment){
									}
								});
							} catch (Exception e){
								e.printStackTrace();
							}
						}
						@Override
						public void failed(Throwable exc,Object attchment){
						}
					});
				}
				@Override
				public void failed(Throwable exc,Object attachment){
				}
		});

		Thread.sleep(1000);
	}
}
