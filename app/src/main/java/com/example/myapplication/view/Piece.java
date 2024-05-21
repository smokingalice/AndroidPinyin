package com.example.myapplication.view;

import android.graphics.Point;


public class Piece {

    private PieceImage pieceImage;

    private int beginX;

    private int beginY;

    private int indexX;

    private int indexY;


    public Piece(int indexX, int indexY) {
        this.indexX = indexX;
        this.indexY = indexY;
    }


    public Point getCenter() {
        return new Point(getBeginX() + getPieceImage().getImage().getWidth()
                / 2, getBeginY() + getPieceImage().getImage().getHeight() / 2);
    }


    public boolean isSameImage(Piece otherPieceImage) {
        if (pieceImage == null) {
            if (otherPieceImage.pieceImage != null) {
                return false;
            }
        }
        // 当两个Piece封装图片资源ID相同时，即可认为这两个Piece上的图片相同。
        return pieceImage.getImageId() == otherPieceImage.pieceImage.getImageId();
    }


    public int getBeginX() {
        return beginX;
    }


    public void setBeginX(int beginX) {
        this.beginX = beginX;
    }


    public int getBeginY() {
        return beginY;
    }


    public void setBeginY(int beginY) {
        this.beginY = beginY;
    }


    public int getIndexX() {
        return indexX;
    }


    public void setIndexX(int indexX) {
        this.indexX = indexX;
    }


    public int getIndexY() {
        return indexY;
    }


    public void setIndexY(int indexY) {
        this.indexY = indexY;
    }


    public PieceImage getPieceImage() {
        return pieceImage;
    }


    public void setPieceImage(PieceImage pieceImage) {
        this.pieceImage = pieceImage;
    }
}
