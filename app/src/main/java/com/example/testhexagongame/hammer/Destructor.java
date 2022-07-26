package com.example.testhexagongame.hammer;

import com.example.testhexagongame.tiles.tile.Box;
import com.example.testhexagongame.tiles.tile.Shape.Shape;

public interface Destructor<T extends Shape, U> {
    public Boolean destroy(Box<T, U> box);
}
