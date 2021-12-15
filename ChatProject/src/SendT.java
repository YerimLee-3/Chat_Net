

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
	int cnt = 0;
	// 질문, 답 string 배열
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
	        System.out.printf("사용자 이름 입력: ");
	        username = sc.next();
	        System.out.printf(username+"님이 입장(IP주소"+m_Socket.getLocalAddress()+" "+m_Socket.getInetAddress()+")\n");
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
					mode = "ques";
				}
				
				if (mode.equals("chat")) {
					sendString = username + " : " + sendString;
					sendWriter.println(sendString);
					sendWriter.flush();
					
				} else if (mode.equals("ques")) {
					
					sendQuestion(m_Socket);
					sendString = username + " : " + "1번째 질문! " + question[0];
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
		// 개수 입력받기
		Scanner sc = new Scanner(System.in);
        // 배열 생성
		question = new String[3];
		answer = new String[3];
		
		for(int i=0; i<3; i++) {
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
	
	public void answerQuestion(String senderChat) {
		if (cnt >= 3) {
			mode = "chat";
		} 
		if(senderChat.equals(answer[cnt])) { // 답과 일치하면
            sendWriter.println(username + " : " + "질문을 맞추었습니다!");
            sendWriter.flush();
        } else {
            sendWriter.println(username + " : " + "질문을 틀렸습니다!");
            sendWriter.flush();
        }
		cnt++;
		
		if (cnt >= 3) {
			mode = "chat";
		} else {
			sendWriter.println(username + " : " + cnt+"번째 질문! "+ question[cnt]);
	        sendWriter.flush();
		}
		
        
	}
}
