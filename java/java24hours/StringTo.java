public class StringTo {
		private String str;

		public StringTo (String str) {
				this.str = str;
		}

		public void change() {
				System.out.println("float: " + Float.parseFloat(str));
				System.out.println("Float: " + new Float(str));
				System.out.println("int: " + Integer.parseInt(str));
		}

		public static void main(String[] args) {
				StringTo str_to = new StringTo(args[0]);
				str_to.change();
				return;
		}
}
