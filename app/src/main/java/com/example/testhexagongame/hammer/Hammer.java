package com.example.testhexagongame.hammer;

import com.example.testhexagongame.tiles.tile.Box;
import com.example.testhexagongame.tiles.tile.Shape.Shape;

public class Hammer<T extends Shape, U> implements Destructor<T, U> {
    private final U baseItem;

    public Hammer(U baseItem) {
        this.baseItem = baseItem;
    }

    @Override
    public Boolean destroy(Box<T, U> box) {
        if (box.getData() != baseItem) {
            box.setData(baseItem);
            return true;
        }
        return false;
    }
}
