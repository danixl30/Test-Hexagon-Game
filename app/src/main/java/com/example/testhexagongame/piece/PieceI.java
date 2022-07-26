package com.example.testhexagongame.piece;

import com.example.testhexagongame.tiles.tile.Box;
import com.example.testhexagongame.tiles.tile.Shape.Shape;

public interface PieceI<T extends Shape, U> {
    public void create();
    public Boolean putPiece(Box<T, U> current);
    public Boolean isFit(Box<T, U> current);
}