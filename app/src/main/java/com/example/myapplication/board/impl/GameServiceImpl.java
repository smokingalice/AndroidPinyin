package com.example.myapplication.board.impl;

import android.graphics.Point;
import android.media.MediaPlayer;

import com.example.myapplication.R;
import com.example.myapplication.board.AbstractBoard;
import com.example.myapplication.board.GameService;
import com.example.myapplication.utils.GameConf;
import com.example.myapplication.utils.LinkInfo;
import com.example.myapplication.view.Piece;

import java.util.Random;


public class GameServiceImpl implements GameService {
    /**
     * 定义一个Piece[][]数组
     */
    private Piece[][] pieces;
    /**
     * 游戏配置对象
     */
    private GameConf config;

    /**
     * 构造方法
     *
     * @param config 游戏配置对象
     */
    public GameServiceImpl(GameConf config) {
        // 将游戏的配置对象设置本类中
        this.config = config;
    }

    @Override
    public void start(int n) {
        // 定义一个AbstractBoard对象
        AbstractBoard board = null;
        Random random = new Random();
        // 获取一个随机数, 可取值0、1、2、3四值。
        int index = random.nextInt(2);
        // 随机生成AbstractBoard的子类实例
        if(n==1) {
            switch (index) {
                case 0:
                    // 0返回VerticalBoard(竖向)
                    board = new VerticalBoard();
                    break;
                case 1:
                    // 1返回HorizontalBoard(横向)
                    board = new HorizontalBoard();
                    break;

            }
        }
        else{  board = new FullBoard();
            }
        // 初始化Piece[][]数组
        this.pieces = board.create(config);
    }

    @Override
    public Piece[][] getPieces() {
        return this.pieces;
    }

    @Override
    public boolean hasPieces() {
        // 遍历Piece[][]数组的每个元素
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                // 只要任意一个数组元素不为null，也就是还剩有非空的Piece对象
                if (pieces[i][j] != null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 根据触碰点的位置查找相应的方块
     */
    @Override
    public Piece findPiece(float touchX, float touchY) {
        /*
         * 由于在创建Piece对象的时候, 将每个Piece的开始座标加了
         * GameConf中设置的beginImageX、beginImageY值, 因此这里要减去这个值
         */
        int relativeX = (int) touchX - this.config.getBeginImageX();
        int relativeY = (int) touchY - this.config.getBeginImageY();
        /*
         * 如果鼠标点击的地方比board中第一张图片的开始x座标和开始y座标要小, 即没有找到相应的方块
         */
        if (relativeX < 0 || relativeY < 0) {
            return null;
        }
        /*
         * 获取relativeX座标在Piece[][]数组中的第一维的索引值 ，第二个参数为每张图片的宽
         */
        int indexX = getIndex(relativeX, GameConf.PIECE_WIDTH);
        /*
         * 获取relativeY座标在Piece[][]数组中的第二维的索引值 ，第二个参数为每张图片的高
         */
        int indexY = getIndex(relativeY, GameConf.PIECE_HEIGHT);
        // 这两个索引比数组的最小索引还小, 返回null
        if (indexX < 0 || indexY < 0) {
            return null;
        }
        // 这两个索引比数组的最大索引还大(或者等于), 返回null
        if (indexX >= this.config.getXSize()
                || indexY >= this.config.getYSize()) {
            return null;
        }
        // 返回Piece[][]数组的指定元素
        return this.pieces[indexX][indexY];
    }

    /**
     * 工具方法：计算相对于Piece[][]数组的第一维 或第二维的索引值
     *
     * @param relative 座标
     * @param size     每张图片边的长或者宽
     * @return
     */
    private int getIndex(int relative, int size) {
        // 表示座标relative不在该数组中，数组下标从0开始
        int index = -1;
        /*
         * 让座标除以边长, 没有余数, 索引减1， 例如点了x座标为20, 边宽为10, 20 % 10 没有余数, index为1,
         * 即在数组中的索引为1(第二个元素)
         */
        if (relative % size == 0) {
            index = relative / size - 1;
        } else {
            /*
             * 有余数, 例如点了x座标为21, 边宽为10, 21 % 10有余数, index为2， 即在数组中的索引为2(第三个元素)
             */
            index = relative / size;
        }
        return index;
    }

    @Override
    public LinkInfo link(Piece p1, Piece p2) {
        MediaPlayer mediaplayer1= MediaPlayer.create(config.getContext(), R.raw.mright);
        // 两个Piece是同一个, 即选中了同一个方块, 返回null
        if (p1.equals(p2)) {
            return null;
        }
        Point p1Point = p1.getCenter();
        // 获取p2的中心点
        Point p2Point = p2.getCenter();
        // 如果p1的图片与p2的图片不相同, 则返回null
        if (p1.isSameImage(p2)) {
            mediaplayer1.start();
            return new LinkInfo(p1Point, p2Point);

        }
        // 如果p2在p1的左边, 则需要重新执行本方法, 两个参数互换
        if (p2.getIndexX() < p1.getIndexX()) {
            return link(p2, p1);
        }
        // 获取p1的中心点

        // 情况1：如果两个Piece在同一行，并且可以直接相连

        /*
         * 情况4：两个Piece以三条线段相连，有两个转折点的情况。 该map的key存放第一个转折点,
         * value存放第二个转折点,map的size()说明有多少种可以连的方式
         */

        return null;
    }
}
