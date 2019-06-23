public class ModemTester {
		public static void main(String[] args) {
				CableModem surfBoard = new CableModem();
				DslModem gateway = new DslModem();
				AcousticModem acoustic = new AcousticModem();
				
				surfBoard.speed = 50000;
				gateway.speed = 40000;
				acoustic.speed = 300;

				System.out.println("Trying the cable modem:");
				surfBoard.displaySpeed();
				surfBoard.connect();
				surfBoard.disconnect();

				System.out.println("Trying the DSL modem:");
				gateway.displaySpeed();
				gateway.connect();
				gateway.disconnect();

				System.out.println("Trying the acoustic modem:");
				acoustic.displaySpeed();
				acoustic.connect();
				acoustic.disconnect();
		}
}

