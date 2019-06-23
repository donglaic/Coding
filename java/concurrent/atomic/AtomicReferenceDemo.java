import java.util.concurrent.atomic.*;

public class AtomicReferenceDemo {
	static AtomicReference<Integer> money = new AtomicReference<Integer>();
	
	public static void main(String[] args){
		money.set(19);
		for (int i=0;i<1;i++){
			new Thread(){
				public void run(){
					while(true){
						Integer m = money.get();
						if (m<20){
							if (money.compareAndSet(m,m+20)){
								System.out.println("adder:<20 add succ, value:"+money.get());
							}else{
								System.out.println("adder:!<20");
							}
						}
						try {Thread.sleep(100);} catch (InterruptedException e){}
					}
				}
			}.start();
		}
		new Thread(){
			public void run(){
				for(int i=0;i<100;i++){
					while(true){
						Integer m = money.get();
						if(m>10){
							System.out.println("suber:>10");
							if(money.compareAndSet(m,m-10)){
								System.out.println("suber:-10 vluae:"+money.get());
							}else{
								System.out.println("suber:not enought");
							}
						}else{
							System.out.println("suber:<10");
						}
						try {Thread.sleep(100);} catch (InterruptedException e){}
					}
				}
			}
		}.start();
	}
}
