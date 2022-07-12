package com.example.testhexagongame.tiles.tile.Shape;

import com.example.testhexagongame.tiles.tile.Box;
import com.example.testhexagongame.tiles.tile.Box2;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Shape2<T extends Shape2> {
    private final HashMap<String, Box2<T>> sides = new HashMap<>();
    protected final ArrayList<String> sidesRules = new ArrayList<>();
    protected final ArrayList<Integer> rotations = new ArrayList<>();
    private Integer currentRotation = 0;

    public Shape2() {
        setAdjacentRules();
        setRotationRules();
    }

    protected abstract void setAdjacentRules();
    protected abstract void setRotationRules();

    public void setRotation(Integer rotation) {
        if (!rotations.contains(rotation)) return;
        currentRotation = rotation;
    }

    public Integer getCurrentRotation() {
        return currentRotation;
    }

    public void setAdjacent(String side, Box2<T> node) {
        if (!sidesRules.contains(side)) return;
        sides.put(side, node);
    }

    public Box2<T> getSide(String side) {
        return sides.get(side);
    }
}
