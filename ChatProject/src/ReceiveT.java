import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class ReceiveT extends Thread{

	private Socket m_Socket;
	private SendT st;
	private String myA;
	private String senderName;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		
		try {
			BufferedReader tmpbuf = new BufferedReader(new InputStreamReader(m_Socket.getInputStream(),"utf-8"));
			String receiveString;
			while(true)
			{
				receiveString = tmpbuf.readLine();
				System.out.println(receiveString);
				senderName = receiveString.split(":")[0].trim();
				
				if((st.mode.equals("ques"))&&!(senderName.equals(st.username))) {
					myA = receiveString.split(":")[1].trim();
					st.answerQuestion(myA);
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}
	
	public void setSocket(Socket _socket)
	{
		m_Socket = _socket;
	}
	
	public void setSender(SendT sendt) {
		st = sendt;
	}

}
