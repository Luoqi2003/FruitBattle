package shuiguoxiaoxiaole.person;

import java.io.*;
import java.util.ArrayList;

public class DataHandler {
    /**
     * 读取用户的无尽模式最高分
     *
     * @param id 用户ID
     * @return 最高分
     */
    public static int readEndlessScore(String id) {
        File file = new File("data/PersonData/Person/" + id + "/Endless.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int maxScore = 0;
            while ((line = reader.readLine()) != null) {
                int score = Integer.parseInt(line);
                if (score > maxScore) {
                    maxScore = score;
                }
            }
            return maxScore;
        } catch (IOException e) {
            e.printStackTrace();
            return 0; // 如果文件不存在或读取失败，返回默认值0
        }
    }

    /**
     * 写入用户的无尽模式最高分
     *
     * @param id       用户ID
     * @param score    最高分
     * @return 是否成功写入
     */
    public static boolean writeEndlessScore(String id, int score) {
        File file = new File("data/PersonData/Person/" + id + "/Endless.txt");
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(score + "\n");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
