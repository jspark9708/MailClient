import javax.net.ssl.*;
import javax.sql.DataSource;
import java.awt.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

class MailServer{
    public static void MailSender(String From, String PW, String To, String Subject, String Body, String path) throws Exception{

        String Reply;

        //SSLSocket을 만들기 위해서 SSLSocketFactory를 선언, 기본 SSLSocketFactory를 return 하는 함수
        SSLSocketFactory sslsocketf = (SSLSocketFactory) SSLSocketFactory.getDefault();
        System.out.println("연결 시작");
        //SSLSocket socket = (SSLSocket) socketFactory.createSocket("smtp.gmail.com", 587);

        //Secure Socket Layer(암호화 기반 인터넷 보안 프로토콜)를 이용하여 gmail smtp 서버, 465 port에 연결
        SSLSocket socket = (SSLSocket)sslsocketf.createSocket("smtp.gmail.com", 465);
        System.out.println("연결 성공");

        //메일을 받는 서버의 응답을 보기위해서 socket에 InputStream 부착
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        //자동 개행을 위해서 PrintWriter 클래스 사용, socket에 부착하여 command를 server에 보내는 역할
        PrintWriter outToServer = new PrintWriter(socket.getOutputStream(), true);

        //연결을 시도하고 응답을 받는다. Status가 220이면 연결 성공
        //220: The server is ready (response to the client’s attempt to establish a TCP connection)
        Reply = inFromServer.readLine();
        if(Reply.startsWith("220"))
            System.out.println("서버 연결 성공!");
        else {
            System.out.println("서버 연결 실패");
            System.exit(0);
        }
        System.out.println(Reply);

        //HELO command: SMTP 세션과 대화를 시작
        //250: The requested command is completed. As a rule, the code is followed by OK
        outToServer.println("HELO gmail.com");
        Reply = inFromServer.readLine();
        ReplyCheck(Reply, "250");

        //본인 gmail id와 pw 넣기, Base64로 encoding
        String originalInput = From;
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
        String originalInput2 = PW;
        String encodedString2 = Base64.getEncoder().encodeToString(originalInput2.getBytes());

        //AUTH LOGIN command: 서버에 대한 클라이언트 인증, 사용자의 아이디와 암호로 인증
        //334: Response to the AUTH command when the requested security mechanism is accepted
        //235: Authentication successful (response to AUTH)
        outToServer.println("AUTH LOGIN");
        Reply = inFromServer.readLine();
        ReplyCheck(Reply, "334");
        outToServer.println(encodedString);
        Reply = inFromServer.readLine();
        ReplyCheck(Reply, "334");
        outToServer.println(encodedString2);
        Reply = inFromServer.readLine();
        ReplyCheck(Reply, "235");

        //MAIL FROM command: 보낸이 메일 지정
        outToServer.println("MAIL FROM:<" + From + ">");
        Reply = inFromServer.readLine();
        ReplyCheck(Reply, "250");

        //RCPT TO command: 받는이 메일 지정
        outToServer.println("RCPT TO: <" + To + ">");
        Reply = inFromServer.readLine();
        ReplyCheck(Reply, "250");

        //DATA command: 메시지 본문 내용 시작
        //354: The server confirms mail content transfer (response to DATA). After that, the client starts sending the mail. Terminated with a period ( “.”)
        outToServer.println("DATA");
        Reply = inFromServer.readLine();
        ReplyCheck(Reply, "354");

        //DATA command 이후에 Subject 보내기위한 header 전송
        String header = "To: " + To + "\r\n";
        header += "From:"+ From +"\r\n";
        header += "Subject:" + hangul(Subject)+"\r\n";
        header += "MIME-Version: 1.0\r\n";//file attach용
        header += "Content-Type: multipart/mixed; boundary=KkK170891tpbkKk__FV_KKKkkkjjwq\r\n";
        outToServer.println(header + "\r\n");

        String mimeBody = ("--KkK170891tpbkKk__FV_KKKkkkjjwq\r\n");
        mimeBody += ("Content-Type: text/plain; charset=\"utf-8\"\r\n\r\n");
        mimeBody += Body + "\r\n";
        mimeBody += "\r\n";


        //첨부파일 경로 가져와서 해당 파일의 mimeType 확인 후 이것을 attatch하도록

        /******************************/
        Path Source = Paths.get(path);
        String mimeType = Files.probeContentType(Source);
        String filename = Source.getFileName().toString();
        System.out.println(filename);

        mimeBody += ("--KkK170891tpbkKk__FV_KKKkkkjjwq\r\n");
        mimeBody += ("Content-Type: " + mimeType + "\r\n");
        mimeBody += ("Content-Transfer-Encoding: utf-8\r\n");
        mimeBody += ("Content-Disposition: attachment;\r\n filename=" + filename + "\r\n");
        mimeBody += "\r\n";

        BufferedReader reader = new BufferedReader(
                new FileReader(path, Charset.forName("UTF-8"))
        );

        String filebody;
        while ((filebody = reader.readLine()) != null) {
            mimeBody += hangul(filebody) + "\r\n";
        }

        reader.close();
        /******************************/
        //String filename = "info.txt";
        //String filebody = "파일 내용입니다";
        //String filepath = "info.txt";

        mimeBody += "--KkK170891tpbkKk__FV_KKKkkkjjwq--\r\n";
        outToServer.println(mimeBody);

        outToServer.println("\r\n.\r\n");
        Reply = inFromServer.readLine();
        ReplyCheck(Reply, "250");

        //QUIT command: 연결 종료
        //221: The server closes the transmission channel
        outToServer.println("QUIT");
        Reply = inFromServer.readLine();
        ReplyCheck(Reply, "221");

        //mail 보낸 후 socket close
        outToServer.close();
        inFromServer.close();
        socket.close();
    }

    //response와 status code를 비교하여서 메일 보내는 것이 성공하고 있는지 확인 및 response 출력
    public static void ReplyCheck(String Reply, String Status) throws Exception{
        if(!Reply.startsWith(Status))
            throw new Exception(Reply);
        System.out.println(Reply);
    }

    //수정 없이 보낼경우 한글파일은 깨지므로, 유니코드 변환
    public static String hangul(String Unicodestr) throws UnsupportedEncodingException{
        if(Unicodestr==null) return null;
        return new String(Unicodestr.getBytes("UTF-8"));
    }

    //프로그램 실행
    public static void run() {
        try {
            MailGUI frame = new MailGUI();
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]){
        MailServer.run();
    }
}