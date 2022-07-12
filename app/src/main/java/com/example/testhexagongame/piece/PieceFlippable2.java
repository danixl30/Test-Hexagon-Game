package com.example.testhexagongame.piece;

import com.example.testhexagongame.tiles.tile.Shape.Shape2;

public interface PieceFlippable2<T extends Shape2> extends PieceI2<T> {
    public void flip();
}
