public class DslModem2 extends Modem2 {
    public DslModem2() {
        method = "DSL connection";
    }

    public void connect() {
        System.out.println("Connecting to the Internet ...");
        System.out.println("Using a " + method);
    }
}
