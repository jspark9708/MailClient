import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Choice;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")//The serializable class OboeObject does not declare a static final serialVersionUID field of type long 방지
public class MailGUI extends JFrame {
	
	
	private JPanel contentPane;
	private JTextField senderField;
	private JLabel receiverForm;
	private Choice pwField;
	private JTextField receiverField;
	private JLabel subjectForm;
	private JTextField subjectField;
	private JLabel bodyForm;
	private JTextField bodyField;

	public MailGUI() {
		
		setTitle("메일 전송");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setBounds(100, 100, 700, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		/*From 라벨*/
		JLabel senderForm = new JLabel("보내는 사람");
        senderForm.setFont(new Font("굴림", Font.PLAIN, 15));
        senderForm.setBounds(35, 35, 100, 30);
        contentPane.add(senderForm);

        /*발신메일 입력 필드*/
        senderField = new JTextField();

        senderField = new JTextField("your-email-here@gmail.com");
        senderField.setBounds(150, 35, 200, 30);
        contentPane.add(senderField);
        senderField.setColumns(10);
        senderField.setEditable(true);
        
        /*비밀번호 입력 필드*/
        //choice를 통해서 비밀번호 찾아 일일히 입력하는 과정 간략
        pwField = new Choice();
        pwField.setBounds(350, 35, 200, 30);
        pwField.setFont(new Font("굴림", Font.PLAIN, 20));
        pwField.add(" --------------------------- ");
        //아래에 google app 비밀번호 입력하기
        pwField.add("first password");
        pwField.add("second password");
        pwField.add("third password");
        pwField.add("forth password");
        add(pwField);

        /*To 라벨*/
        receiverForm = new JLabel("받는 사람");
        receiverForm.setFont(new Font("굴림", Font.PLAIN, 15));
        receiverForm.setBounds(35, 85, 100, 30);
        contentPane.add(receiverForm);

        /*수신메일 입력 필드*/
        receiverField = new JTextField();
        receiverField = new JTextField("receiver-email-here@naver.com");
        receiverField.setColumns(10);
        receiverField.setBounds(150, 85, 400, 30);
        contentPane.add(receiverField);

        /*제목 라벨*/
        subjectForm = new JLabel("제목");
        subjectForm.setFont(new Font("굴림", Font.PLAIN, 15));
        subjectForm.setBounds(35, 135, 100, 30);
        contentPane.add(subjectForm);

        /*제목 입력 필드*/
        subjectField = new JTextField();
        subjectField.setColumns(10);
        subjectField.setBounds(150, 135, 400, 30);
        contentPane.add(subjectField);

        /*Body 라벨*/
        bodyForm = new JLabel("내용");
        bodyForm.setFont(new Font("굴림", Font.PLAIN, 15));
        bodyForm.setBounds(35, 211, 100, 30);
        contentPane.add(bodyForm);

        /*내용 입력 필드*/
        bodyField = new JTextField();
        bodyField.setColumns(10);
        bodyField.setBounds(150, 213, 400, 272);
        contentPane.add(bodyField);

        /*전송 버튼*/
        JButton sendButton = new JButton("전송");

        sendButton.setBounds(280, 517, 150, 23);
        contentPane.add(sendButton);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {

            	String From = senderField.getText();
            	String PW = pwField.getSelectedItem();
            	String To = receiverField.getText();
            	String Subject = subjectField.getText();
            	String Body = bodyField.getText();
               
            	if (From.length() *  To.length() * Body.length() == 0) { //Subject는 없어도 <제목없음>으로 전송되므로
            		JOptionPane.showMessageDialog(null, "메일 폼을 작성해주세요!");
            	}
               
            	try{
            		MailServer.MailSender(From, PW, To, Subject, Body);
            		JOptionPane.showMessageDialog(null, "메일 보내기 성공!", "메일 전송 상태", JOptionPane.PLAIN_MESSAGE);
            		System.out.println("==========================");
            		System.out.println("메일 보내기 성공!");
            	}
            	catch(Exception e){
            		JOptionPane.showMessageDialog(null, "메일이 발송되지 않았습니다.", "메일 전송 상태", JOptionPane.ERROR_MESSAGE);
            		System.out.println("==========================");
            		System.out.println("메일이 발송되지 않았습니다.");
            		System.out.println(e.toString());
            	}
           	}
            
        });
    }
}