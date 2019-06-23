import java.io.*;

public class Cper {

		Cper(String src) {
				try {
					File src_file = new File(src);
					FileInputStream inStream = new FileInputStream(src_file);
					File tar_file = new File(src + ".new");
					FileOutputStream outStream = new FileOutputStream(tar_file);
					int in = 0;
					char inChar;
					do {
							in = inStream.read();
							inChar = (char)in;
							if (in != -1) {
									outStream.write(in);
							} 
					} while (in != -1);
					inStream.close();
					outStream.close();
				} catch (IOException ioe) {
						System.out.println("IO error " + ioe.getMessage());
				}
		}

		public static void main(String[] args) {
				Cper cp = new Cper(args[0]);
		}
}
	

