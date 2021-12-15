

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
	// QuestionMode : ���� ���� 3��
	int cnt = 0;
	// ����, �� string �迭
	public String[] question;
	public String[] answer;
	public String mode = "chat";
	
	public String username;
	public String sendString;
	
	PrintWriter sendWriter;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		try {
			BufferedReader tmpbuf = new BufferedReader(new InputStreamReader(System.in));
			sendWriter = new PrintWriter(new OutputStreamWriter(m_Socket.getOutputStream(), "utf-8"));
			
			
			
			Scanner sc = new Scanner(System.in);
	        System.out.printf("����� �̸� �Է�: ");
	        username = sc.next();
	        System.out.printf(username+"���� ����(IP�ּ�"+m_Socket.getLocalAddress()+" "+m_Socket.getInetAddress()+")\n");
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
					mode = "ques";
				}
				
				if (mode.equals("chat")) {
					sendString = username + " : " + sendString;
					sendWriter.println(sendString);
					sendWriter.flush();
					
				} else if (mode.equals("ques")) {
					
					sendQuestion(m_Socket);
					sendString = username + " : " + "1��° ����! " + question[0];
					sendWriter.println(sendString);
					sendWriter.flush();	
					
				} 
				
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
		// ���� �Է¹ޱ�
		Scanner sc = new Scanner(System.in);
        // �迭 ����
		question = new String[3];
		answer = new String[3];
		
		for(int i=0; i<3; i++) {
			System.out.printf("������ ������ �Է����ּ��� : ");
			question[i] = sc.next();
			System.out.printf("������ ���� �Է����ּ��� : ");
			answer[i] = sc.next();
		}	
	}
	
	public void setSocket(Socket _socket)
	{
		m_Socket = _socket;
	}
	
	public void answerQuestion(String senderChat) {
		if (cnt >= 3) {
			mode = "chat";
		} 
		if(senderChat.equals(answer[cnt])) { // ��� ��ġ�ϸ�
            sendWriter.println(username + " : " + "������ ���߾����ϴ�!");
            sendWriter.flush();
        } else {
            sendWriter.println(username + " : " + "������ Ʋ�Ƚ��ϴ�!");
            sendWriter.flush();
        }
		cnt++;
		
		if (cnt >= 3) {
			mode = "chat";
		} else {
			sendWriter.println(username + " : " + cnt+"��° ����! "+ question[cnt]);
	        sendWriter.flush();
		}
		
        
	}
}
