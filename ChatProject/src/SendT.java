

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SendT extends Thread{

	private Socket m_Socket;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		try {
			BufferedReader tmpbuf = new BufferedReader(new InputStreamReader(System.in));
			
			PrintWriter sendWriter = new PrintWriter(new OutputStreamWriter(m_Socket.getOutputStream(), "utf-8"));
			
			String username;
			String sendString;
			
			Scanner sc = new Scanner(System.in);
	        System.out.printf("����� �̸� �Է�: ");
	        username = sc.next();
			
	        System.out.println("�������� �������� 'questionMode' �Է�");
			while(true)
			{
				sendString = tmpbuf.readLine();
				if(sendString.equals("exit"))
				{
					break;
				}
				if(sendString.equals("questionMode"))
				{
					break;
				}
				sendString = username + " : " + sendString;
				sendWriter.println(sendString);
				sendWriter.flush();
			}
			
			sendWriter.close();
			tmpbuf.close();
			m_Socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void sendQuestion(Socket _socket)
	{
		// ���� ���� �ȵ�
	}
	
	public void setSocket(Socket _socket)
	{
		m_Socket = _socket;
	}
	
}
