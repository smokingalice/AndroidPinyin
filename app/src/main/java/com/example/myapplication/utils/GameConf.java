package com.example.myapplication.utils;

import android.content.Context;

public class GameConf {

    public final static int PIECE_X_SUM = 6;

    public final  static int PIECE_Y_SUM = 6;

    public final  static int BEGIN_IMAGE_X = 10;

    public final  static int BEGIN_IMAGE_Y = 50;

    public static int PIECE_WIDTH;

    public static int PIECE_HEIGHT;

    public static int DEFAULT_TIME = 110;

    private int xSize;

    private int ySize;

    private int beginImageX;

    private int beginImageY;

    private long gameTime;

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
