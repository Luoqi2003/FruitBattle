package shuiguoxiaoxiaole.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Mxkn
 * @version 1.8.0_301
 */

public class PersonInf {
    /**
     * 个人信息界面。
     * @param stage 游戏主界面
     */
    public  static void  load(Stage stage){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Index.class.getResource("/fxml/personInf.fxml")));
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
