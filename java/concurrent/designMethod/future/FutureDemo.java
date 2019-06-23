interface Data {
	public String getResult();
}

class FutureData implements Data {
	protected RealData realdata = null; //FutureData is packaging of RealData
	protected boolean isReady = false;

	public synchronized void setRealData(RealData realdata){
		if(isReady){return;}
		this.realdata = realdata;
		isReady=true;
		notifyAll(); //RealData ready, notify getResult()
	}

	public synchronized String getResult(){
		// will wait RealData constructed
		while(!isReady){
			try{
				wait(); // wait until RealData ready
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		return realdata.result;
	}
}

class RealData implements Data {
	protected final String result;

	public RealData(String para){
		StringBuffer sb = new StringBuffer();
		for (int i=0;i<10;i++){
			sb.append(para);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}
		result = sb.toString();
	}

	public String getResult(){return result;}
}

class Client {
	public Data request(final String queryStr){
		final FutureData future = new FutureData();
		new Thread(){
			public void run(){
				RealData realdata = new RealData(queryStr);
				future.setRealData(realdata);
			}
		}.start();
		return future;
	}
}

public class FutureDemo {
	public static void main(String[] args){
		Client client = new Client();
		Data data = client.request("name");
		System.out.println("request done");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e){
		}
		System.out.println("data="+data.getResult());
	}
}

