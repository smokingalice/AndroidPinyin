package com.example.myapplication.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import com.example.myapplication.board.GameService;
import com.example.myapplication.utils.ImageUtil;
import com.example.myapplication.utils.LinkInfo;

import java.util.List;


public class GameView extends View {

    private GameService gameService;

    private Piece selectedPiece;

    private LinkInfo linkInfo;

    private Paint paint;

    private Bitmap selectImage;


    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.paint = new Paint();
        // 设置连接线的颜色
        this.paint.setColor(Color.GREEN);
        // 设置连接线的粗细
        this.paint.setStrokeWidth(20);
        // 初始化被选中的图片
        this.selectImage = ImageUtil.getSelectImage(context);
    }


    public void setLinkInfo(LinkInfo linkInfo) {
        this.linkInfo = linkInfo;
    }


    public void setSelectedPiece(Piece piece) {
        this.selectedPiece = piece;
    }


    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.gameService == null) {
            return;
        }
        Piece[][] pieces = gameService.getPieces();
            if (pieces != null) {
                // 遍历pieces二维数组
                for (int i = 0; i < pieces.length; i++) {
                    for (int j = 0; j < pieces[i].length; j++) {
                        // 如果二维数组中该元素不为空（即有方块），将这个方块的图片画出来
                        if (pieces[i][j] != null) {
                            // 得到这个Piece对象
                            Piece piece = pieces[i][j];
                            if (piece.getPieceImage() != null) {
                                // 根据方块左上角X、Y座标绘制方块
                                canvas.drawBitmap(piece.getPieceImage().getImage(), piece.getBeginX(), piece.getBeginY(), null);
                            }
                        }
                    }
                }
        }
        // 如果当前对象中有linkInfo对象, 即连接信息
        if (this.linkInfo != null) {
            // 绘制连接线
            drawLine(this.linkInfo, canvas);
            // 处理完后清空linkInfo对象
            this.linkInfo = null;
        }
        // 画选中标识的图片
        if (this.selectedPiece != null) {
            canvas.drawBitmap(this.selectImage, this.selectedPiece.getBeginX(),
                    this.selectedPiece.getBeginY(), null);
        }
    }


    private void drawLine(LinkInfo linkInfo, Canvas canvas) {
        // 获取LinkInfo中封装的所有连接点
        List<Point> points = linkInfo.getLinkPoints();
        // 依次遍历linkInfo中的每个连接点
        for (int i = 0; i < points.size() - 1; i++) {
            // 获取当前连接点与下一个连接点
            Point currentPoint = points.get(i);
            Point nextPoint = points.get(i + 1);
            // 绘制连线
            canvas.drawLine(currentPoint.x, currentPoint.y, nextPoint.x,
                    nextPoint.y, this.paint);
        }
    }

    // 开始游戏方法
    public void startGame(int n) {
        this.gameService.start(n);
        this.postInvalidate();
    }
}
