import java.io.*;
import java.net.*;

public class Client {
	public static void main(String[] args) throws IOException {
		Socket client = null;
		PrintWriter writer = null;
		BufferedReader reader = null;

		try {
			client = new Socket();
			client.connect(new InetSocketAddress("localhost",8000));
			writer = new PrintWriter(client.getOutputStream(),true);
			writer.println("Hello!");
			writer.flush();

			reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
			System.out.println("from server: "+reader.readLine());
		} catch (UnknownHostException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		} finally {
			if(writer != null) writer.close();
			if(reader != null) reader.close();
			if(client != null) client.close();
		}
	}
}
