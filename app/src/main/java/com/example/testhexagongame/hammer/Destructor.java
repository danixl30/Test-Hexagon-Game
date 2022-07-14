package com.example.testhexagongame.hammer;

import com.example.testhexagongame.tiles.tile.Box2;
import com.example.testhexagongame.tiles.tile.Shape.Shape2;

public interface Destructor<T extends Shape2, U> {
    public void destroy(Box2<T, U> box);
}
