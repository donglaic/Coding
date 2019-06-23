import java.util.concurrent.atomic.*;

public class AtomicStampedReferenceDemo {
	static AtomicStampedReference<Integer> money = new AtomicStampedReference<Integer>(19,0);
	
	public static void main(String[] args){
		final int timestamp=money.getStamp();
		for (int i=0;i<3;i++){
			new Thread(){
				public void run(){
					while(true){
						Integer m = money.getReference();
						if (m<20){
							if (money.compareAndSet(m,m+20,timestamp,timestamp+1)){
								System.out.println("<20 add succ, value:"+money.getReference());
								break;
							}else{
								break;
							}
						}
					}
				}
			}.start();
		}
		new Thread(){
			public void run(){
				for(int i=0;i<100;i++){
					while(true){
						int timestamp = money.getStamp();
						Integer m = money.getReference();
						if(m>10){
							System.out.println(">10");
							if(money.compareAndSet(m,m-10,timestamp,timestamp+1)){
								System.out.println("-10 vluae:"+money.getReference());
								break;
							}else{
								System.out.println("not enought");
								break;
							}
						}
						try {Thread.sleep(100);} catch (InterruptedException e){}
					}
				}
			}
		}.start();
	}
}
