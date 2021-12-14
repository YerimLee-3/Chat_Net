import java.io.IOException;
import java.net.Socket;

public class PcClient {
	
	public static void main(String[] args)
	{
		try {

			//Socket c_socket = new Socket("192.168.0.23", 8888); // 192.168.0.1
			Socket c_socket = new Socket("192.168.0.5", 8888);
			
			ReceiveT rec_thread = new ReceiveT();
			rec_thread.setSocket(c_socket);
			
			SendT send_thread = new SendT();
			send_thread.setSocket(c_socket);
			
			send_thread.start();
			rec_thread.start();
			
			// System.out.println("¼º°ø");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
