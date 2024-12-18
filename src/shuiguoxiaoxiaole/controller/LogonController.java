package shuiguoxiaoxiaole.controller;

import shuiguoxiaoxiaole.Director;
import shuiguoxiaoxiaole.person.PersonDirectory;
import shuiguoxiaoxiaole.sound.SoundEffect;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * @author Mxkn
 * @version 1.8.0_301
 */

public class LogonController {

    @FXML
    private ImageView leave;

    @FXML
    private ImageView finish;

    @FXML
    private TextField name;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField apassword;

    @FXML
    private TextField email;

    @FXML
    private TextField phone;

    @FXML
    void mouseClickedFinish(MouseEvent event) {
        boolean flag = true;

        // 检查所有字段是否非空
        if (name.getText().length() == 0 || password.getText().length() == 0
                || apassword.getText().length() == 0 || email.getText().length() == 0 || phone.getText().length() == 0) {
            event.consume();
            Director.getInstance().WarningAlertCreat("注册失败", "请将信息填写完整！");
            flag = false;
        }
        // 检查是否包含非法字符 "-"
        else if (name.getText().contains("-") || password.getText().contains("-")
                || apassword.getText().contains("-") || email.getText().contains("-") || phone.getText().contains("-")) {
            event.consume();
            Director.getInstance().WarningAlertCreat("注册失败", "请勿填写“-”字符！");
            flag = false;
        }
        // 检查两次密码是否一致
        else if (!password.getText().equals(apassword.getText())) {
            event.consume();
            Director.getInstance().WarningAlertCreat("注册失败", "两次密码输入不一致！请重新输入！");
            flag = false;
        }
        // 检查用户名是否已存在
        else if (PersonDirectory.getInstance().isNameExists(name.getText())) {
            event.consume();
            Director.getInstance().WarningAlertCreat("注册失败", "该用户名已存在，请选择其他用户名！");
            flag = false;
        }

    /*
     如果flag为true，说明不符合上述任何情况，则注册成功，
     将新注册的用户加入PersonDirectory，并存入文件
    */
        if (flag) {
            PersonDirectory.getInstance().addNewPerson(name.getText(), password.getText(), email.getText(), phone.getText());
            PersonDirectory.getInstance().writePerson();

            // 使用用户名作为账号
            String accountId = name.getText();

            // 创建登录记录
            PersonDirectory.getInstance().txtLogonCreat(accountId);

            event.consume();
            Director.getInstance().InformationAlertCreat("注册成功！", "您的账号是：" + accountId + "\n" + "您的密码是：" + password.getText());
            Director.getInstance().toLogin();
        }
    }

    /**
     * 点击离开键返回主页
     */
    @FXML
    void mouseClickedLeave() {
        Director.getInstance().toLogin();
        SoundEffect.play1();
    }

    /**
     * 从此往下均为鼠标进出按钮，设置透明度效果的函数
     */
    @FXML
    void mouseEnteredFinish() {
        finish.setOpacity(0.8);
    }

    @FXML
    void mouseEnteredLeave() {
        leave.setOpacity(0.8);
    }

    @FXML
    void mouseExitedFinish() {
        finish.setOpacity(1);
    }

    @FXML
    void mouseExitedLeave() {
        leave.setOpacity(1);
    }

}
