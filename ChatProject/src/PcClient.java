import java.io.IOException;
import java.net.Socket;

public class PcClient {
	
	public static void main(String[] args)
	{
		try {

			Socket c_socket = new Socket("192.168.0.23", 8888); // 192.168.0.1
			//Socket c_socket = new Socket("192.168.0.5", 8888);
			
			
			SendT send_thread = new SendT();
			send_thread.setSocket(c_socket);
			
			ReceiveT rec_thread = new ReceiveT();
			rec_thread.setSocket(c_socket);
			rec_thread.setSender(send_thread);
			
			send_thread.start();
			rec_thread.start();
			
			// System.out.println("����");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
