import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class FileChooser extends JFrame{
    public String Path;
    public FileChooser(){
        //JFrame window = new JFrame();
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setFileFilter(new FileNameExtensionFilter("txt", "txt"));
        //파일오픈 다이얼로그 를 띄움
        int result = fileChooser.showOpenDialog(null);

        if(result == JFileChooser.APPROVE_OPTION){
            //선택한 파일의 경로 반환
            File selectedFile = fileChooser.getSelectedFile();
            //경로 출력
            Path = selectedFile.getPath();
            //System.out.println(Path);

        }

    }
}
