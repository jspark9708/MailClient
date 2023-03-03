import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class MailGUI extends JFrame {

    private JPanel contentPane;

    private JTextField senderField;
    private JLabel receiverForm;
    private Choice pwField;
    private JTextField receiverField;
    private JLabel subjectform;
    private JTextField subjectField;
    private JLabel bodyForm;
    private JTextField bodyField;

    private JLabel filename;

    public MailGUI() {
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
        pwField.add("put your Google App Pw here");
        pwField.add("put your Google App Pw here");
        pwField.add("put your Google App Pw here");
        pwField.add("put your Google App Pw here");
        add(pwField);

        /*To 라벨*/
        receiverForm = new JLabel("받는 사람");
        receiverForm.setFont(new Font("굴림", Font.PLAIN, 15));
        receiverForm.setBounds(35, 85, 100, 30);
        contentPane.add(receiverForm);

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

        JButton btnFile = new JButton("파일 첨부");
        btnFile.setBounds(35, 180, 100, 23);
        contentPane.add(btnFile);

        filename = new JLabel();
        final String[] path = new String[1];
        filename.setBounds(150, 180, 400, 30);
        contentPane.add(filename);

        btnFile.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                try {
                    FileChooser file = new FileChooser();
                    path[0] = file.Path.toString();
                    filename.setText(path[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        /*전송 버튼*/
        JButton sendButton = new JButton("메일 보내기");

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
                String filepath = filename.getText();
                if (From.length() *  To.length() * (filepath.length() + Body.length())== 0) { //Subject는 없어도 <제목없음>으로 전송되므로
                    JOptionPane.showMessageDialog(null, "메일 폼을 작성해주세요!");
                }
                else {
                    try {
                        MailServer.MailSender(From, PW, To, Subject, Body, filepath);
                        JOptionPane.showMessageDialog(null, "메일 보내기 성공!");
                        System.out.println("==========================");
                        System.out.println("메일 보내기 성공");
                        bodyField.setText("");
                        subjectField.setText("");
                        filename.setText("");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "메일 보내기 실패!");
                        System.out.println("==========================");
                        System.out.println("메일 보내기 실패");
                        System.out.println(e.toString());
                    }
                }
            }
        });

    }
}      