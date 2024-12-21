package shuiguoxiaoxiaole;

import shuiguoxiaoxiaole.person.PersonDirectory;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Director.getInstance().init(primaryStage);
    }

    public static void main(String[] args) {
        try {
            // 确保目录存在
            File dataDir = new File("data/PersonData");
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }
            
            File personListFile = new File("data/PersonData/personList.txt");
            if (!personListFile.exists()) {
                personListFile.createNewFile();
            }
            
            //读取储存在文件里的用户信息
            PersonDirectory.getInstance().setPersons(
                PersonDirectory.getInstance().creatPerson("data/PersonData/personList.txt")
            );
        } catch (IOException e) {
            System.out.println("Warning: Could not load person data, starting with empty list");
            // 即使文件操作失败，也继续运行程序
        }
        
        Application.launch(args);
    }
}
