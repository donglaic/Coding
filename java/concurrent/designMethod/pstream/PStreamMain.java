import java.util.concurrent.*;

public class PStreamMain{
	public static void main(String[] args){
		new Thread(new Plus()).start();
		new Thread(new Multiply()).start();
		new Thread(new Div()).start();
		for(int i=1;i<=1000;i++){
			for (int j=1;j<=1000;j++){
				Msg msg=new Msg();
				msg.i = i;
				msg.j = j;
				msg.orgStr = "(("+i+"+"+j+")*"+i+")/2";
				Plus.bq.add(msg);
			}
		}
	}
}

class Msg{
	public double i;
	public double j;
	public String orgStr = null;
}

class Plus implements Runnable {
	public static BlockingQueue<Msg> bq = new LinkedBlockingQueue<Msg>();
	@Override
	public void run(){
		while(true){
			try {
				Msg msg = bq.take();
				msg.j=msg.i+msg.j;
				Multiply.bq.add(msg);
			} catch (InterruptedException e){}
		}
	}
}

class Multiply implements Runnable {
	public static BlockingQueue<Msg> bq = new LinkedBlockingQueue<Msg>();
	@Override
	public void run(){
		while(true){
			try {
				Msg msg = bq.take();
				msg.i=msg.i*msg.j;
				Div.bq.add(msg);
			} catch (InterruptedException e){}
		}
	}
}

class Div implements Runnable {
	public static BlockingQueue<Msg> bq = new LinkedBlockingQueue<Msg>();
	@Override
	public void run(){
		while(true){
			try {
				Msg msg = bq.take();
				msg.i=msg.i/2;
				System.out.println(msg.orgStr+"="+msg.i);
			} catch (InterruptedException e){}
		}
	}
}
