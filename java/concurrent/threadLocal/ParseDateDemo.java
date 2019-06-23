import java.util.concurrent.*;
import java.text.*;
import java.util.*;

public class ParseDateDemo {
	//static ThreadLocal<SimpleDateFormat> tl = new ThreadLocal<SimpleDateFormat>();
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static class ParseDate implements Runnable {
		int i = 0;
		public ParseDate(int i) {this.i = i;}
		public void run(){
			try {
				Date t = sdf.parse("2019-05-31 19:23:11");
				System.out.println(i+":"+t);
			} catch (ParseException e){
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args){
		ExecutorService es = Executors.newFixedThreadPool(10);
		ParseDate pd = new ParseDate(1);
		for (int i=0;i<1000;i++){
			es.execute(pd);
		}
	}
}
