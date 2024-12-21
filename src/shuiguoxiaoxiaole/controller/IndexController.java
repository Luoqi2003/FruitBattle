package shuiguoxiaoxiaole.controller;

import shuiguoxiaoxiaole.Director;

import shuiguoxiaoxiaole.sound.SoundEffect;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import shuiguoxiaoxiaole.physics.PhysicsManager;

import java.util.Optional;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.Random;
import javafx.scene.layout.Pane;

/**
 * @author Mxkn
 * @version 1.8.0_301
 */

public class IndexController {

    @FXML
    private ImageView aboutUs;

    @FXML
    private ImageView startGame;

    @FXML
    private ImageView setting;

    @FXML
    private ImageView personInf;

    @FXML
    private ImageView returnGame;

    @FXML
    private ImageView help;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private AnchorPane fruitPane;

    @FXML
    public void initialize() {
        // 在初始化时设置物理系统的游戏面板
        PhysicsManager physicsManager = PhysicsManager.getInstance();
        physicsManager.setGamePane(fruitPane);
        
        // 创建Timeline用于定时生成水果
        Random random = new Random();
        Timeline fruitDropTimeline = new Timeline(
            new KeyFrame(Duration.seconds(2), event -> {
                int fruitCount = random.nextInt(3) + 1;
                for (int i = 0; i < fruitCount; i++) {
                    double x = random.nextDouble() * (Director.WIDTH - 40);
                    physicsManager.addElementWithRandomFruit(x, 0);
                }
            })
        );
        
        fruitDropTimeline.setCycleCount(Timeline.INDEFINITE);
        physicsManager.start();
        fruitDropTimeline.play();
    }

    @FXML
    void mouseClickedPersonInf() {
        Director.getInstance().toPersonInf();
    }

    @FXML
    void mouseClickedSetting() {
        Director.getInstance().toSetting();
    }

    @FXML
    void mouseClickedHelp() {
        Director.getInstance().toHelpSelect();
    }

    @FXML
    void mouseClickedAboutus() {
        Director.getInstance().toAboutUs();
    }

    @FXML
    void mouseClickedStartGame() {
        Director.getInstance().toModelSelect();
    }

    /**
     * 点击离开键返回主页，生成弹窗二次确认
     */
    @FXML
    void mouseClickedReturn(MouseEvent event) {
        event.consume();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("温馨提示");
        alert.setHeaderText(null);
        alert.initOwner(Director.getInstance().getStage());
        alert.setContentText("您确定要退出游戏吗？");
        Optional<ButtonType> result =alert.showAndWait();
        SoundEffect.play1();
        if(result.get() == ButtonType.OK) {
            Director.getInstance().toLogin();
        }
    }

    /**
     * 从此往下均为鼠标进出按钮，设置透明度效果的函数
     */
    @FXML
    void mouseEnterPersonInf() {
        personInf.setOpacity(0.8);
    }

    @FXML
    void mouseEnterHelp() {
        help.setOpacity(0.8);
    }

    @FXML
    void mouseEnterSetting() {
        setting.setOpacity(0.8);
    }

    @FXML
    void mouseEnterAboutus() {
        aboutUs.setOpacity(0.8);
    }

    @FXML
    void mouseEnterStartGame() {
        startGame.setOpacity(0.8);
    }

    @FXML
    void mouseEnterReturn() {
        returnGame.setOpacity(0.6);
    }

    @FXML
    void mouseExitedPersonInf() {
        personInf.setOpacity(1);
    }

    @FXML
    void mouseExitedHelp() {
        help.setOpacity(1);
    }

    @FXML
    void mouseExitedSetting() {
        setting.setOpacity(1);
    }

    @FXML
    void mouseExitedAboutus() {
        aboutUs.setOpacity(1);
    }

    @FXML
    void mouseExitedStartGame() {
        startGame.setOpacity(1);
    }

    @FXML
    void mouseExitedReturn() {
        returnGame.setOpacity(1);
    }

    @FXML
    public void handleMouseClick(MouseEvent event) {
        if (event == null) {
            return;
        }
        
        // 获取物理系统管理器
        PhysicsManager physicsManager = PhysicsManager.getInstance();
        
        // 获取鼠标点击位置
        double x = event.getX();
        double y = event.getY();
        
        // 添加水果到物理系统
        physicsManager.addElementWithRandomFruit(x, y);
        
        // 播放音效
        SoundEffect.play1();        
    }

}
