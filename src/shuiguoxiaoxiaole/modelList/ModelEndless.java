package shuiguoxiaoxiaole.modelList;

/**
 *
 * @author yishui
 * @version 1.8.0_301
 * @see Model
 *
 */
public class ModelEndless implements Model {
    // 添加地图大小常量
    private static final int LEFT = 10;
    private static final int RIGHT = 10;
    
    private int score;  // 玩家得分
    private int[][] map;  // 游戏地图

    /**
     * 新建一个ModelNormal类
     */
    private ModelNormal modelNormal;

    /**
     * 设定游戏时间
     * 单位为毫秒
     */
    private long time = 180000;  // 默认时间为3分钟（180000毫秒）

    /**
     * 记录上一次时间更新的时间戳
     */
    private long start = 0;

    /**
     * 设置时间函数是否运行的标志
     * 开始为正常运行
     */
    private boolean is = true;

    /**
     * 构造函数
     */
    public ModelEndless() {
        modelNormal = new ModelNormal();
    }

    /**
     * 设置等级
     * @param level
     */
    public void setLevel(int level) {
        modelNormal.setLevel(level);
    }

    /**
     * 返回当前地图
     *
     * @return 当前地图��组
     */
    @Override
    public int[][] getMapNow() {
        return modelNormal.getMap();
    }

    /**
     * 生成初始地图
     *
     * @return 初始地图数组
     */
    @Override
    public int[][] getMapZero() {
        modelNormal.generateMap();  // 使用 ModelNormal 的方法生成地图
        return modelNormal.getMap();  // 直接返回生成的地图
    }

    /**
     * 返回交换后的地图
     *
     * @param a1 第一个元素的行索引
     * @param a2 第一个元素的列索引
     * @param b1 第二个元素的行索引
     * @param b2 第二个元素的列索引
     * @return 交换后的地图数组
     */
    @Override
    public int[][] getMapSwapped(int a1, int a2, int b1, int b2) {
        modelNormal.swapBlock(a1, a2, b1, b2);
        return modelNormal.getMap();
    }

    /**
     * 判读是否可以消去，
     * 如果可以消去，直接消去
     * 如果消去不了，返回-1
     *
     * @return 消去检查结果
     */
    @Override
    public int getMapSwappedResult() {
        return modelNormal.checkMap();
    }

    /**
     * 返回交换检查后的数值，详见"数据结构.md"
     * 如果返回消去检查后的地图,请直接getMapNow
     *
     * @return 交换检查结果
     */
    @Override
    public int getMapJudge() {
        // 添加边界检查
        int[][] map = modelNormal.getMap();
        if (map == null || map.length == 0 || map[0].length == 0) {
            return -1;
        }
        return modelNormal.checkMap();
    }

    /**
     * 返回确定下落几格后的地图
     *
     * @return 下落几格后的地图数组
     */
    @Override
    public int[][] getMapDropChecked() {
        modelNormal.setDropBlock();
        return modelNormal.getMap();
    }

    /**
     * 返回下落后的地图
     *
     * @return 下落后的地图数组
     */
    @Override
    public int[][] getMapAfter() {
        modelNormal.dropBlock();
        return modelNormal.getMap();
    }

    /**
     * 获取本关卡的当前得分
     *
     * @return 玩家得分
     */
    public int getScore() {
        return modelNormal.getScore();
    }

    /**
     * 获取限定时间（秒）
     *
     * @return 限定时间（秒）
     */
    @Override
    public int getTimeLimit() {
        return (int) (time / 1000);
    }

    /**
     * 设置限定时间（秒）
     *
     * @param timeLimit 限定时间（秒）
     */
    @Override
    public void setTimeLimit(int timeLimit) {
        this.time = timeLimit * 1000;  // 将秒转换为毫秒
    }

    /**
     * 计算硬币数量
     *
     * @return 计算后的硬币数量
     */
    @Override
    public int calculateCoins() {
        // 假设每 100 分转换为 1 枚金��
        return modelNormal.getScore() / 100;
    }

    /**
     * 获取本关卡的剩余时间
     *
     * @return 剩余时间（秒）
     */
    public int getRemainTime() {  // 移除了 @Override 注解
        if (is) {
            is = false;
            start = System.currentTimeMillis();  // 初始化 start 时间
        }
        try {
            long currentTime = System.currentTimeMillis();  // 获取当前时间
            time -= (currentTime - start);  // 更新剩余时间
            start = currentTime;  // 更新 start 时间为当前时间
        } catch (Exception e) {
            System.out.println("Got an exception!");
        }
        return Math.max(0, (int) (time / 1000));  // 确保剩余时间不低于0
    }

    /**
     * 判断当前游戏是否结束
     * 如果时间为0，则游戏结束
     *
     * @return 是否游戏结束
     */
    public boolean GameEnd() {  // 移除了 @Override 注解
        if (time <= 0) {
            // 游戏结束，计算金币
            int coins = calculateCoins();

            // 保存金币到全局状态或传递给战斗系统
            GameManager.getInstance().setCoins(coins);

            // 通知战斗系统进入战斗模式
            BattleSystem.startBattleWithCoins(coins);

            return true;
        }
        return false;
    }

    /**
     * 获取当前的时间函数工作状态
     * 如果正在运行返回true
     *
     * @return 时间函数工作状态
     */
    public boolean isIs() {
        return is;
    }

    /**
     * 重新设置当前的时间函数工作状态
     *
     * @param is 时间函数工作状态
     */
    public void setIs(boolean is) {
        this.is = is;
    }

    // 添加生成地图的方法
    private void generateMap() {
        for (int i = 0; i < LEFT; i++) {
            for (int j = 0; j < RIGHT; j++) {
                map[i][j] = ((int) (Math.random() * 4) + 1) * 10000000;
                while (map[i][j] == 0) {
                    map[i][j] = ((int) (Math.random() * 4) + 1) * 10000000;
                }
            }
        }
    }
}