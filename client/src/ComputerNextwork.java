import java.awt.EventQueue;
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
public class ComputerNextwork extends JFrame {

	private JPanel contentPane;
	
	private JTextField senderField;
	private JLabel sendform;
	
	private JTextField receiverField;
	private JLabel titleform;
	private JTextField titleField;
	private JLabel bodyForm;
	private JTextField bodyField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ComputerNextwork frame = new ComputerNextwork();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ComputerNextwork() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		int errorflag = 0;
		
		/*발신자 From 표시*/
		JLabel fromform = new JLabel("From");
		fromform.setFont(new Font("굴림", Font.PLAIN, 20));
		fromform.setBounds(36, 32, 100, 30);
		contentPane.add(fromform);
		
		/*발신메일 입력 필드*/
		senderField = new JTextField();
		senderField.setBounds(148, 35, 400, 30);
		contentPane.add(senderField);
		senderField.setColumns(10);
		
		/*수신칸 표시*/
		sendform = new JLabel("Send to");
		sendform.setFont(new Font("굴림", Font.PLAIN, 20));
		sendform.setBounds(36, 85, 100, 30);
		contentPane.add(sendform);
		
		/*수신메일 입력 필드*/
		receiverField = new JTextField();
		receiverField.setColumns(10);
		receiverField.setBounds(148, 88, 400, 30);
		contentPane.add(receiverField);
		
		/*제목 표시*/
		titleform = new JLabel("Title");
		titleform.setFont(new Font("굴림", Font.PLAIN, 20));
		titleform.setBounds(36, 145, 100, 30);
		contentPane.add(titleform);
		
		/*제목 입력 받을필드*/
		titleField = new JTextField();
		titleField.setColumns(10);
		titleField.setBounds(148, 148, 400, 30);
		contentPane.add(titleField);
		
		/*Body 표시*/
		bodyForm = new JLabel("Body");
		bodyForm.setFont(new Font("굴림", Font.PLAIN, 20));
		bodyForm.setBounds(36, 211, 100, 30);
		contentPane.add(bodyForm);
		
		/*내용 입력받을 필드*/
		bodyField = new JTextField();
		bodyField.setColumns(10);
		bodyField.setBounds(148, 213, 400, 272);
		contentPane.add(bodyField);
		
		/*전송 버튼*/
		JButton sendButton = new JButton("SEND");
		
		sendButton.setBounds(301, 517, 91, 23);
		contentPane.add(sendButton);
		
		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//혹시나 메일이 loss되는 경우가 발생할 경우
				if(errorflag == 1) {
					JOptionPane.showMessageDialog(null, "성공적으로 보내졌습니다");
				}
				else {
					JOptionPane.showMessageDialog(null, "메일전송이 되지않았습니다");
					System.exit(0);
				}
			}
		});
	}
}
