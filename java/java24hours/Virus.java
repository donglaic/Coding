public class Virus {
		static int virusCount = 0;

		private int newSeconds = 86;
		public String author = "virus";
		int maxFileSize = 30000;

		public void setNewSeconds(int value) {
				if (value >= 60 && value <= 100) {
						newSeconds = value;
				}
		}

		public boolean infectFile(String filename) {
				boolean success = false;
				// file - infecting statements go here
				return success;
		}

		public int getSeconds() {
				return newSeconds;
		}

		public void setSecondes(int newValue) {
				if (newValue > 60) {
						newSeconds = newValue;
				}
		}

		void tauntUser() {
				System.out.println("That has gotta hurt!");
		}

		void tauntUser(String taunt) {
				System.out.println(taunt);
		}

		public Virus() {
				virusCount++;
		}

		public Virus(String name, int size) {
				author = name;
				maxFileSize = size;
		}

		static void showVirusCount() {
				System.out.println("There are " + virusCount + " viruse");
		}

		static int getVirusCount() {
				return virusCount;
		}
}
