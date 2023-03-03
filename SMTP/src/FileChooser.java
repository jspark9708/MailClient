import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class FileChooser extends JFrame{
	private static final long serialVersionUID = 1576658171392892649L;
	public String Path;
    public FileChooser(){
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setFileFilter(new FileNameExtensionFilter("txt", "txt"));
        //파일오픈 다이얼로그를 띄움
        int result = fileChooser.showOpenDialog(null);

        if(result == JFileChooser.APPROVE_OPTION){
            //선택한 파일의 경로 반환
            File selectedFile = fileChooser.getSelectedFile();
            //경로 출력
            Path = selectedFile.getPath();
        }

    }
}
