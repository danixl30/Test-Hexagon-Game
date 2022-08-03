package com.example.testhexagongame.piece;

import com.example.testhexagongame.tiles.tile.Box;
import com.example.testhexagongame.tiles.tile.Shape.Shape;

public interface DataChecker<T extends Shape, U, Sides> {
    Boolean check(Box<T, U, Sides> box1, Box<T, U, Sides> box2);
}
