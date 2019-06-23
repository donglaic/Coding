public class CableModem2 extends Modem2 {
    public CableModem2() {
        method = "cable connection";
    }

    public void connect() {
        System.out.println("Connecting to the Internet ...");
        System.out.println("Using a " + method);
    }
}
