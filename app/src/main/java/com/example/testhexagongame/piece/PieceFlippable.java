package com.example.testhexagongame.piece;

import com.example.testhexagongame.tiles.tile.Shape.Shape;

public interface PieceFlippable<T extends Shape, U, Sides> extends Piece<T, U, Sides> {
    public void flip();
}
