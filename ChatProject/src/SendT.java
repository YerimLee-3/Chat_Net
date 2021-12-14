

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
	int cnt = 3;
	// ����, �� string �迭
	String[] question;
	String[] answer;
	
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
					sendQuestion(m_Socket);
					for(int j=0; j<cnt; j++) {
						sendString = j+1 + "��° ���� : " + question[j];
						sendWriter.println(sendString);
						sendWriter.flush();
					}
					new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            try {
                                if(senderChat.equals(question[cnt])) { // ��� ��ġ�ϸ�
                                    sendWriter.println(UserID + " : " + "������ ���߾����ϴ�!");
                                    sendWriter.flush();
                                } else {
                                    sendWriter.println(UserID + " : " + "������ Ʋ�Ƚ��ϴ�!");
                                    sendWriter.flush();
                                }
                                sendWriter.println(UserID + " : " + "��° ����! "+ sendmsg);
                                sendWriter.flush();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
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
		// ���� �Է¹ޱ�
		Scanner sc = new Scanner(System.in);
        // �迭 ����
		question = new String[cnt];
		answer = new String[cnt];
		
		for(int i=0; i<cnt; i++) {
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
	
}
