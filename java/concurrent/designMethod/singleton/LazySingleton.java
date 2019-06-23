public class LazySingleton {
	private LazySingleton(){
		System.out.println("LazySingleton is create");
	}

	private static LazySingleton instance = null;

	public static synchronized LazySingleton getInstance(){
		if (instance==null){
			instance = new LazySingleton();
		}
		return instance;
	}

	public static void main(String[] args){
		LazySingleton s = LazySingleton.getInstance();
		LazySingleton s1 = LazySingleton.getInstance();
		//System.out.println(LazySingleton.STATUS);
	}
}
