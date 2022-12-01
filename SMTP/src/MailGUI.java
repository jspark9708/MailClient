
        import javax.swing.JFrame;
        import javax.swing.JPanel;
        import javax.swing.border.EmptyBorder;
//import java.awt.BorderLayout;//사용 X
        import javax.swing.JLabel;
        import java.awt.Font;
        import java.awt.event.*;
        import javax.swing.JTextField;
        import javax.swing.JButton;
        import javax.swing.JOptionPane;

@SuppressWarnings("serial")//The serializable class OboeObject does not declare a static final serialVersionUID field of type long 방지
public class MailGUI extends JFrame {

    private JPanel contentPane;

    private JTextField senderField;
    private JLabel sendform;
    private JTextField pwField;
    private JTextField receiverField;
    private JLabel subjectform;
    private JTextField subjectField;
    private JLabel bodyForm;
    private JTextField bodyField;

    /**
     * Create the frame.
     */
    public MailGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);


        /*발신자 From 표시*/
        JLabel fromform = new JLabel("보내는 사람");
        fromform.setFont(new Font("굴림", Font.PLAIN, 15));
        fromform.setBounds(35, 35, 100, 30);
        contentPane.add(fromform);

        /*발신메일 입력 필드*/
        senderField = new JTextField();
        senderField.setBounds(150, 35, 200, 30);
        contentPane.add(senderField);
        senderField.setColumns(10);
        senderField.setEditable(true);
        
        /*비밀번호 입력 필드*/
        pwField = new JTextField();
        pwField.setBounds(350, 35, 200, 30);
        contentPane.add(pwField);
        pwField.setColumns(10);
        pwField.setText("password를 입력하세요");
        pwField.setEditable(true);
        
        /*수신칸 표시*/
        sendform = new JLabel("받는 사람");
        sendform.setFont(new Font("굴림", Font.PLAIN, 15));
        sendform.setBounds(35, 85, 100, 30);
        contentPane.add(sendform);

        /*수신메일 입력 필드*/
        receiverField = new JTextField();
        receiverField.setColumns(10);
        receiverField.setBounds(150, 88, 400, 30);
        contentPane.add(receiverField);

        /*제목 표시*/
        subjectform = new JLabel("제목");
        subjectform.setFont(new Font("굴림", Font.PLAIN, 15));
        subjectform.setBounds(35, 145, 100, 30);
        contentPane.add(subjectform);

        /*제목 입력 받을필드*/
        subjectField = new JTextField();
        subjectField.setColumns(10);
        subjectField.setBounds(150, 145, 400, 30);
        contentPane.add(subjectField);

        /*Body 표시*/
        bodyForm = new JLabel("내용");
        bodyForm.setFont(new Font("굴림", Font.PLAIN, 15));
        bodyForm.setBounds(35, 211, 100, 30);
        contentPane.add(bodyForm);

        /*내용 입력받을 필드*/
        bodyField = new JTextField();
        bodyField.setColumns(10);
        bodyField.setBounds(150, 213, 400, 272);
        contentPane.add(bodyField);

        /*전송 버튼*/
        JButton sendButton = new JButton("메일 보내기");

        sendButton.setBounds(280, 517, 150, 23);
        contentPane.add(sendButton);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String From = senderField.getText();
                String PW = pwField.getText();
                String To = receiverField.getText();
                String Subject = subjectField.getText();
                String Data = bodyField.getText();
                try{
                    MailServer.MailSender(From, PW, To, Subject, Data);
                    JOptionPane.showMessageDialog(null, "메일 보내기 성공!");
                    System.out.println("메일 보내기 성공!");
                }catch(Exception e){
                	JOptionPane.showMessageDialog(null, "메일 보내기 실패!");
                    System.out.println("메일 보내기 실패!");
                    System.out.println(e.toString());
                }
            }
        });
    }
}