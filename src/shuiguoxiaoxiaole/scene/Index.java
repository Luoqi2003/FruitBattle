package shuiguoxiaoxiaole.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import shuiguoxiaoxiaole.physics.PhysicsManager;

/**
 * @author Mxkn
 * @version 1.8.0_301
 */


public class Index {
    /**
     * 游戏主页面。
     * @param stage 游戏主界面
     */
    public static void load(Stage stage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Index.class.getResource("/fxml/index.fxml")));
            stage.getScene().setRoot(root);
            
            // 设置物理系统
            PhysicsManager physicsManager = PhysicsManager.getInstance();
            physicsManager.setGamePane((AnchorPane)root);
            physicsManager.start();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
