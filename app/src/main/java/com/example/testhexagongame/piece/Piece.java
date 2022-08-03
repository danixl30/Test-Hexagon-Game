package com.example.testhexagongame.piece;

import com.example.testhexagongame.tiles.tile.Box;
import com.example.testhexagongame.tiles.tile.Shape.Shape;

public interface Piece<T extends Shape, U, Sides> {
    public void create();
    public Boolean putPiece(Box<T, String, Sides> current, Integer orientation);
    public Boolean isFit(Box<T, U, Sides> current);
}