package com.example.testhexagongame.piece;

import com.example.testhexagongame.tiles.tile.Box2;
import com.example.testhexagongame.tiles.tile.Shape.Shape2;

public interface PieceI2<T extends Shape2, U> {
    public void create();
    public Boolean putPiece(Box2<T, U> current);
}