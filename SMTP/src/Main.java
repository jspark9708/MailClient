import javax.net.ssl.*;
import java.awt.*;
import java.io.*;
import java.util.Base64;

class MailServer{
    public static void MailSender(String From, String PW, String To, String Subject, String Data) throws Exception{

        String Reply;

        SSLSocketFactory sslsocketf = (SSLSocketFactory) SSLSocketFactory.getDefault();
        System.out.println("연결 시작");
        SSLSocket socket = (SSLSocket)sslsocketf.createSocket("smtp.gmail.com", 465);

        System.out.println("연결 성공");

        //메일을 받는 서버의 응답을 보기위해서 socket에 InputStream 부착
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter outToServer = new PrintWriter(socket.getOutputStream(), true);

        Reply = inFromServer.readLine();
        if(Reply.startsWith("220"))
            System.out.println("서버 연결 성공!");
        else {
            System.out.println("서버 연결 실패");
            System.exit(0);
        }

        System.out.println(Reply);

        //HELO command
        outToServer.println("HELO gmail.com");
        Reply = inFromServer.readLine();
        ReplyCheck(Reply, "250");

        //AUTH command
        //본인 gmail id와 pw 넣기
        String originalInput = From;
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
        String originalInput2 = PW;
        String encodedString2 = Base64.getEncoder().encodeToString(originalInput2.getBytes());

        outToServer.println("AUTH LOGIN");
        Reply = inFromServer.readLine();
        ReplyCheck(Reply, "334");
        outToServer.println(encodedString);
        Reply = inFromServer.readLine();
        ReplyCheck(Reply, "334");
        outToServer.println(encodedString2);
        Reply = inFromServer.readLine();
        ReplyCheck(Reply, "235");

        //MAIL FROM command
        outToServer.println("MAIL FROM:<" + From + ">");
        Reply = inFromServer.readLine();
        ReplyCheck(Reply, "250");

        //RCPT TO command
        outToServer.println("RCPT TO: <" + To + ">");
        Reply = inFromServer.readLine();
        ReplyCheck(Reply, "250");
        
        //DATA command
        outToServer.println("DATA");
        
        //DATA command 이후에 Subject 보내기위한 header 전송
        String header = "To: " + To + "\r\n";
        header += "From:"+ From +"\r\n";
        header += "Subject:" + hangul(Subject)+"\r\n";
        outToServer.println(header+"\r\n");
        
        Reply = inFromServer.readLine();
        ReplyCheck(Reply, "354");
        outToServer.println(Data);
        outToServer.println(".");
        Reply = inFromServer.readLine();
        ReplyCheck(Reply, "250");

        //QUIT command
        outToServer.println("QUIT");
        Reply = inFromServer.readLine();
        ReplyCheck(Reply, "221");

        //mail 보낸 후 socket close
        outToServer.close();
        inFromServer.close();
        socket.close();
    }

    public static void ReplyCheck(String Reply, String Status) throws Exception{
        if(!Reply.startsWith(Status))
            throw new Exception(Reply);
        System.out.println(Reply);
    }
    
    //수정 없이 보낼경우 한글파이른 깨지므로, 유니코드 변환
    public static String hangul(String Unicodestr) throws UnsupportedEncodingException{
  	  if(Unicodestr==null) return null;
  	  return new String(Unicodestr.getBytes("utf-8"));
    }
    public static void main(String args[]){
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MailGUI frame = new MailGUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}