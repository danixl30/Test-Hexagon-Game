package com.example.testhexagongame.piece;

import com.example.testhexagongame.tiles.tile.Shape.Shape;

public interface PieceFlippable<T extends Shape, U> extends PieceI<T, U> {
    public void flip();
}
