package com.example.myapplication.utils;

import android.content.Context;

public class GameConf {
    /**
     * X轴有几个方块
     */
    public final static int PIECE_X_SUM = 6;
    /**
     * Y轴有几个方块
     */
    public final  static int PIECE_Y_SUM = 6;
    /**
     * 从哪里开始画第一张图片出现的x座标
     */
    public final  static int BEGIN_IMAGE_X = 10;
    /**
     * 从哪里开始画第一张图片出现的y座标
     */
    public final  static int BEGIN_IMAGE_Y = 50;

    /**
     * 连连看的每个方块的图片的宽   启动的时候赋值
     */
    public static int PIECE_WIDTH;
    /**
     * 连连看的每个方块的图片的高   启动的时候赋值
     */
    public static int PIECE_HEIGHT;
    /**
     * 记录游戏的总事件（200秒）.
     */
    public static int DEFAULT_TIME = 110;
    /**
     * Piece[][]数组第一维的长度
     */
    private int xSize;
    /**
     * Piece[][]数组第二维的长度
     */
    private int ySize;
    /**
     * Board中第一张图片出现的x座标
     */
    private int beginImageX;
    /**
     * Board中第一张图片出现的y座标
     */
    private int beginImageY;
    /**
     * 记录游戏的总时间, 单位是秒
     */
    private long gameTime;
    /**
     * 应用上下文
     */
    private Context context;


    public GameConf(int xSize, int ySize, int beginImageX, int beginImageY,
                    long gameTime, Context context) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.beginImageX = beginImageX;
        this.beginImageY = beginImageY;
        this.gameTime = gameTime;
        this.context = context;
    }

    public long getGameTime() {
        return gameTime;
    }


    public int getXSize() {
        return xSize;
    }


    public int getYSize() {
        return ySize;
    }

    public int getBeginImageX() {
        return beginImageX;
    }


    public int getBeginImageY() {
        return beginImageY;
    }


    public Context getContext() {
        return context;
    }
}
