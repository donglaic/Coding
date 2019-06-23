public class DslModem extends Modem {
		String method = "dsl connection";
		public void connect() {
				System.out.println("Connecting to the Internet...");
				System.out.println("Using a " + method);
				}
}
