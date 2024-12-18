package shuiguoxiaoxiaole.modelList;

public class GameManager {
    private static GameManager instance;
    private int coins;

    // 单例模式
    private GameManager() {}

    public static synchronized GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    /**
     * 设置金币数量
     * @param coins 金币数量
     */
    public void setCoins(int coins) {
        this.coins = coins;
    }

    /**
     * 获取金币数量
     * @return 金币数量
     */
    public int getCoins() {
        return coins;
    }
}