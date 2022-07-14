package com.example.testhexagongame.piece;

import com.example.testhexagongame.tiles.tile.Shape.Shape2;

public interface PieceFlippable2<T extends Shape2, U> extends PieceI2<T, U> {
    public void flip();
}
