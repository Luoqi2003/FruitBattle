package shuiguoxiaoxiaole.modelList;

/**
 *
 * @author yishui
 * @version 1.8.0_301
 *
 */
public interface Model {

    /**
     * 返回当前地图
     * @return 当前地图数组
     */
    int[][] getMapNow();

    /**
     * 生成初始地图
     * @return 初始地图数组
     */
    int[][] getMapZero();

    /**
     * 返回交换后的地图
     * @param a1 第一个元素的行索引
     * @param a2 第一个元素的列索引
     * @param b1 第二个元素的行索引
     * @param b2 第二个元素的列索引
     * @return 交换后的地图数组
     */
    int[][] getMapSwapped(int a1, int a2, int b1, int b2);

    /**
     * 返回交换检查后的数值，详见“数据结构.md”
     * 返回消去检查后的地图,请直接getMapNow
     * @return 交换检查结果
     */
    int getMapJudge();

    /**
     * 返回确定下落几格后的地图
     * @return 下落几格后的地图数组
     */
    int[][] getMapDropChecked();

    /**
     * 返回下落后的地图
     * @return 下落后的地图数组
     */
    int[][] getMapAfter();

    /**
     * 判读是否可以消去，如果消去不了，返回原状
     * @return 返回地图数组
     */
    int getMapSwappedResult();

    /**
     * 获取限定时间（秒）
     * @return 限定时间（秒）
     */
    int getTimeLimit();

    /**
     * 设置限定时间（秒）
     * @param timeLimit 限定时间（秒）
     */
    void setTimeLimit(int timeLimit);

    /**
     * 计算金币数量
     * @return 计算后的金币数量
     */
    int calculateCoins();
}