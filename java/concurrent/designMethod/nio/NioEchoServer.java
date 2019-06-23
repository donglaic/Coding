import java.util.concurrent.*;
import java.nio.*;
import java.util.*;
import java.io.*;
import java.net.*;
import java.nio.channels.*;
import java.nio.channels.spi.*;

public class NioEchoServer {
	private Selector selector;
	private static ExecutorService tp = Executors.newCachedThreadPool();
	public static Map<Socket,Long> time_stat= new HashMap<Socket,Long>(10240);

	private void startServer() throws Exception{
		selector = SelectorProvider.provider().openSelector();
		ServerSocketChannel ssc = ServerSocketChannel.open();
		ssc.configureBlocking(false);

		//InetSocketAddress isa = new InetSocketAddress(InetAddress.getLocalHost(),8000);
		InetSocketAddress isa = new InetSocketAddress(8000);
		ssc.socket().bind(isa);

		SelectionKey acceptKey = ssc.register(selector,SelectionKey.OP_ACCEPT);

		for(;;){
			selector.select();
			Set readyKeys = selector.selectedKeys();
			//System.out.println("readyKeys\n"+readyKeys);
			Iterator i = readyKeys.iterator();
			long e=0;
			while(i.hasNext()){
				SelectionKey sk = (SelectionKey)i.next();
				i.remove();

				if(sk.isAcceptable()){
					doAccept(sk);
				}
				else if (sk.isValid() && sk.isReadable()){
					if(!time_stat.containsKey(((SocketChannel)sk.channel()).socket()))
						time_stat.put(((SocketChannel)sk.channel()).socket(),System.currentTimeMillis());
					doRead(sk);
				}
				else if (sk.isValid() && sk.isWritable()){
					doWrite(sk);
					e = System.currentTimeMillis();
					long b = time_stat.remove(((SocketChannel)sk.channel()).socket());
					System.out.println("spend:"+(e-b)+"ms");
				}
			}
		}
	}

	private void doAccept(SelectionKey sk){
		ServerSocketChannel server = (ServerSocketChannel)sk.channel();
		SocketChannel clientChannel;
		try {
			clientChannel = server.accept();
			clientChannel.configureBlocking(false);

			// Register this channel for reading
			SelectionKey clientKey = clientChannel.register(selector,SelectionKey.OP_READ);
			// Allocate an EchoClient instance and attach it to this selection key
			EchoClient echoClient = new EchoClient();
			clientKey.attach(echoClient);

			InetAddress clientAddress = clientChannel.socket().getInetAddress();
			System.out.println("Accepted connection from "+clientAddress.getHostAddress()+".");
		}catch(Exception e){
			System.out.println("Failed to accept new client");
			e.printStackTrace();
		}
	}

	private void disconnect(SelectionKey sk){
		SocketChannel channel = (SocketChannel)sk.channel();
		try {
			channel.close();
		} catch (IOException e){
			System.out.println(e);
		}
	}

	private void doRead(SelectionKey sk){
		SocketChannel channel = (SocketChannel)sk.channel();
		ByteBuffer bb = ByteBuffer.allocate(8192);
		int len;

		try {
			len = channel.read(bb);
			if (len<0){
				disconnect(sk);
				return;
			}
		}catch(Exception e){
			System.out.println("Failed to read from client");
			e.printStackTrace();
			disconnect(sk);
			return;
		}
		bb.flip();
		tp.execute(new HandleMsg(sk,bb));
	}

	private void doWrite(SelectionKey sk){
		SocketChannel channel = (SocketChannel)sk.channel();
		EchoClient echoClient = (EchoClient)sk.attachment();
		LinkedList<ByteBuffer> outq = echoClient.getOutputQueue();

		ByteBuffer bb = outq.getLast();
		try {
			int len = channel.write(bb);
			if (len == -1){
				disconnect(sk);
				return;
			}
			if(bb.remaining()==0){
				// the buffer was completely written, remove it
				outq.removeLast();
			}
		}catch(Exception e){
			System.out.println("Failed to write to client");
			e.printStackTrace();
			disconnect(sk);
			return;
		}
		if(outq.size()==0){
			sk.interestOps(SelectionKey.OP_READ);
		}
	}

	class EchoClient{
		private LinkedList<ByteBuffer> outq;
		EchoClient(){
			outq = new LinkedList<ByteBuffer>();
		}
		public LinkedList<ByteBuffer> getOutputQueue(){
			return outq;
		}
		public void enqueue(ByteBuffer bb){
			outq.addFirst(bb);
		}
	}


	class HandleMsg implements Runnable{
		SelectionKey sk;
		ByteBuffer bb;

		public HandleMsg(SelectionKey sk,ByteBuffer bb){
			this.sk = sk;
			this.bb = bb;
		}

		public void run(){
			EchoClient echoClient = (EchoClient)sk.attachment();
			echoClient.enqueue(bb);
			sk.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
			// fore slector return immediately
			selector.wakeup();
		}
	}

	public static void main(String[] args) {
		NioEchoServer nioserver = new NioEchoServer();
		try {
			nioserver.startServer();
		}catch(IOException e){
			System.out.println(e);
		}catch(Exception e){
			System.out.println(e);
		}
	}
}
