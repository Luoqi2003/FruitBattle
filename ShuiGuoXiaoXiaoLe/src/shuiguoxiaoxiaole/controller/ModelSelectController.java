package shuiguoxiaoxiaole.controller;

import shuiguoxiaoxiaole.Director;
import shuiguoxiaoxiaole.sound.Music;
import shuiguoxiaoxiaole.sound.SoundEffect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;  // 导入 MouseEvent
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class ModelSelectController implements Initializable {

    @FXML
    private ImageView adventure;

    @FXML
    private ImageView endless;

    @FXML
    private ImageView gem;

    @FXML
    private ImageView leave;

    /**
     * 初始化方法，用于设置界面元素的状态
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 禁用或隐藏冒险模式和宝石模式的按钮
        if (adventure != null) {
            adventure.setVisible(false);
            adventure.setDisable(true);
        }
        if (gem != null) {
            gem.setVisible(false);
            gem.setDisable(true);
        }

        // 保留无限模式和返回主页按钮
        if (endless != null) {
            endless.setVisible(true);
            endless.setDisable(false);
        }
        if (leave != null) {
            leave.setVisible(true);
            leave.setDisable(false);
        }
    }

    /**
     * 点击直接进入无尽模式，换音乐
     */
    @FXML
    void clickedEndless(MouseEvent event) {  // 修改为接受 MouseEvent
        Music.getInstance().mediaPlayer.stop();
        Music.getInstance().play2();
        SoundEffect.play1();
        Director.getInstance().endlessStart();
    }

    /**
     * 点击离开键返回主页
     */
    @FXML
    void clickedLeave(MouseEvent event) {  // 修改为接受 MouseEvent
        Director.getInstance().toIndex();
        SoundEffect.play1();
    }

    /**
     * 从此往下均为鼠标进出按钮，设置透明度效果的函数
     */

    @FXML
    void enteredEndless() {
        endless.setOpacity(0.8);
    }

    @FXML
    void exitedEndless() {
        endless.setOpacity(1);
    }

    @FXML
    void enteredLeave() {
        leave.setOpacity(0.6);
    }

    @FXML
    void exitedLeave() {
        leave.setOpacity(1);
    }
}