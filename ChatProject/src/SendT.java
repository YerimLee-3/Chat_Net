

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
	// QuestionMode : 질문 개수 3개
	int cnt = 3;
	// 질문, 답 string 배열
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
	        System.out.printf("사용자 이름 입력: ");
	        username = sc.next();
			
	        System.out.println("질문지를 보내려면 'questionMode' 입력");
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
						sendString = j+1 + "번째 질문 : " + question[j];
						sendWriter.println(sendString);
						sendWriter.flush();
					}
					new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            try {
                                if(senderChat.equals(question[cnt])) { // 답과 일치하면
                                    sendWriter.println(UserID + " : " + "질문을 맞추었습니다!");
                                    sendWriter.flush();
                                } else {
                                    sendWriter.println(UserID + " : " + "질문을 틀렸습니다!");
                                    sendWriter.flush();
                                }
                                sendWriter.println(UserID + " : " + "번째 질문! "+ sendmsg);
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
		// 개수 입력받기
		Scanner sc = new Scanner(System.in);
        // 배열 생성
		question = new String[cnt];
		answer = new String[cnt];
		
		for(int i=0; i<cnt; i++) {
			System.out.printf("질문할 문제를 입력해주세요 : ");
			question[i] = sc.next();
			System.out.printf("문제의 답을 입력해주세요 : ");
			answer[i] = sc.next();
		}	
	}
	
	public void setSocket(Socket _socket)
	{
		m_Socket = _socket;
	}
	
}
