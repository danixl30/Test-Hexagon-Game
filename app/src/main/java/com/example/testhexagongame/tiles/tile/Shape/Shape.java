package com.example.testhexagongame.tiles.tile.Shape;

import com.example.testhexagongame.tiles.tile.Box;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Shape<T extends Shape, U, Sides> {
    private final HashMap<String, Box<T, U, Sides>> sides = new HashMap<>();
    protected final ArrayList<Sides> sidesRules = new ArrayList<>();
    protected final ArrayList<Integer> rotations = new ArrayList<>();
    private Integer currentRotation = 0;

    public Shape() {
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

    public void setAdjacent(String side, Box<T, U, Sides> node) {
        if (!sidesRules.contains(side)) return;
        sides.put(side, node);
    }

    public Box<T, U, Sides> getSide(String side) {
        return sides.get(side);
    }

    public ArrayList<Box<T, U, Sides>> getAdjacents() {
        return new ArrayList<>(sides.values());
    }
}
