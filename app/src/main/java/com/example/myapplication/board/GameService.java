package com.example.myapplication.board;

import com.example.myapplication.utils.LinkInfo;
import com.example.myapplication.view.Piece;


public interface GameService {

    void start(int n);
    Piece[][] getPieces();


    boolean hasPieces();


    Piece findPiece(float touchX, float touchY);


    LinkInfo link(Piece p1, Piece p2);
}
