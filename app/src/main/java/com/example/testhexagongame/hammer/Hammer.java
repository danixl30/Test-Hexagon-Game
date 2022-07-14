package com.example.testhexagongame.hammer;

import com.example.testhexagongame.tiles.tile.Box2;
import com.example.testhexagongame.tiles.tile.Shape.Shape2;

public class Hammer<T extends Shape2, U> implements Destructor<T, U> {
    private final U baseItem;

    public Hammer(U baseItem) {
        this.baseItem = baseItem;
    }

    @Override
    public void destroy(Box2<T, U> box) {
        if (box.getData() != baseItem)
            box.setData(baseItem);
    }
}
