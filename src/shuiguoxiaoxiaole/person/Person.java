package shuiguoxiaoxiaole.person;

import java.io.*;
import java.util.ArrayList;

/**
 * @author yishui,Mxkun
 * @version 1.8.0_301
 */

public class Person {
    private String name; // 昵称
    private String id;   // 用户ID，根据昵称生成
    private String password; // 密码
    private String email; // 邮箱
    private String phone; // 电话号码
    private int score; // 无尽模式得分
    private int coins; // 金币数量
    private int timeLimit; // 限定时间（秒）
    private int[][] endlessData; // 无尽模式数据
    private int endlessScore; // 无尽模式最高分

    /**
     * 注册时的构造函数
     *
     * @param name
     * @param password
     * @param email
     * @param phone
     */
    public Person(String name, String password, String email, String phone) {
        this.name = name;
        this.id = name; // 使用昵称作为ID
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.score = 0;
        this.coins = 0;
        this.timeLimit = 60; // 默认限定时间为60秒
        this.endlessData = new int[20][2]; // 假设无尽模式数据为20行，每行两个值
        this.endlessScore = 0; // 初始化无尽模式得分
    }

    /**
     * 登录时的构造函数
     *
     * @param name
     * @param id
     * @param password
     * @param email
     * @param phone
     */
    public Person(String name, String id, String password, String email, String phone) {
        this.name = name;
        this.id = id; // 使用传入的ID
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.score = 0;
        this.coins = 0;
        this.timeLimit = 60; // 默认限定时间为60秒
        this.endlessData = new int[20][2]; // 假设无尽模式数据为20行，每行两个值
        this.endlessScore = 0; // 初始化无尽模式得分
    }

    /**
     * 将用户数据以字符串形式输出
     * @return
     */
    @Override
    public String toString() {
        return name + '-' + password + '-' + email + '-' + phone;
    }

    /**
     * 重新设计用户属性
     *@param name, password, email, phone
     *@return void
     */
    public void setPerson(String name, String password, String email, String phone) {
        setName(name);
        setPassword(password);
        setEmail(email);
        setPhone(phone);
    }

    /**
     * 获取用户姓名信息
     *@param
     *@return java.lang.String
     */
    public String getName() {
        return name;
    }

    /**
     * 重新设置用户姓名信息
     *@param name
     *@return void
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取用户账号信息
     *@param
     *@return java.lang.String
     */
    public String getId() {
        return id;
    }

    /**
     * 重新设置用户账号信息
     *@param id
     *@return void
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取用户密码信息
     *@param
     *@return java.lang.String
     */
    public String getPassword() {
        return password;
    }

    /**
     * 重新设置用户密码信息
     *@param password
     *@return void
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取用户邮箱信息
     *@param
     *@return java.lang.String
     */
    public String getEmail() {
        return email;
    }

    /**
     * 重新设置用户邮箱信息
     *@param email
     *@return void
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取用户电话信息
     *@param
     *@return java.lang.String
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 重新设置用户电话信息
     *@param phone
     *@return void
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 计算金币数量
     */
    public void calculateCoins() {
        // 直接根据得分计算金币，不再依赖时间
        this.coins += score;
    }

    /**
     * 重新设置无尽模式得分
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * 获取用户无限模式最高分信息
     *@param
     *@return int
     */
    public int getEndlessScore() {
        return endlessScore;
    }

    /**
     * 更新无尽模式最高分
     *
     * @param newScore 新的得分
     */
    public void setEndlessScore(int newScore) {
        if (newScore > this.endlessScore) {
            this.endlessScore = newScore;
            saveEndlessScore(); // 保存最高分到文件
        }
    }

    /**
     * 加载无尽模式最高分
     */
    public void loadEndlessScore() {
        this.endlessScore = DataHandler.readEndlessScore(this.id); // 从文件加载最高分
    }

    /**
     * 保存无尽模式最高分到文件
     */
    public void saveEndlessScore() {
        DataHandler.writeEndlessScore(this.id, this.endlessScore); // 保存最高分到文件
    }

    /**
     * 向passData.txt文件写入数据
     * 只保留无尽模式的数据
     * @return boolean
     */
    public boolean writePassData() {
        File file = new File("data/PersonData/Person/" + id + "/passData.txt");
        FileWriter writeFile = null;
        try {
            writeFile = new FileWriter(file);
            writeFile.write(endlessScore + "\n"); // 只写入无尽模式最高分
            writeFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void someMethod() {
        // 现在可以正常使用 endlessScore
        System.out.println("Endless Score: " + endlessScore);
    }
}