import java.util.concurrent.*;
import java.nio.*;
import java.util.*;
import java.io.*;
import java.net.*;
import java.nio.channels.*;
import java.nio.channels.spi.*;

public class NioClient {
	private Selector selector;
	
	public void init(String ip,int port) throws IOException{
		SocketChannel channel = SocketChannel.open();
		channel.configureBlocking(false);
		this.selector = SelectorProvider.provider().openSelector();
		channel.connect(new InetSocketAddress(ip,port));
		channel.register(selector,SelectionKey.OP_CONNECT);
	}

	public void working() throws IOException{
		while(true){
			if(!selector.isOpen()){
				break;
			}
			selector.select();
			Iterator<SelectionKey> ite = this.selector.selectedKeys().iterator();
			while(ite.hasNext()){
				SelectionKey key = ite.next();
				ite.remove();
				// connection occurred
				if(key.isConnectable()){
					connect(key);
				}else if (key.isReadable()){
					read(key);
				}
			}
		}
	}

	public void connect(SelectionKey key) throws IOException {
		SocketChannel channel = (SocketChannel)key.channel();
		if(channel.isConnectionPending()){
			channel.finishConnect();
		}
		channel.configureBlocking(false);
		channel.write(ByteBuffer.wrap(new String("Hello server!\r\n").getBytes()));
		channel.register(this.selector,SelectionKey.OP_READ);
	}

	public void read(SelectionKey key) throws IOException {
		SocketChannel channel = (SocketChannel)key.channel();
		ByteBuffer buffer = ByteBuffer.allocate(100);
		channel.read(buffer);
		byte[] data = buffer.array();
		String msg = new String(data).trim();
		System.out.println("Client rec: "+msg);
		channel.close();
		key.selector().close();
	}

	public static void main(String[] args) throws IOException{
		NioClient c = new NioClient();
		c.init("127.0.0.1",8000);
		c.working();
	}
}
