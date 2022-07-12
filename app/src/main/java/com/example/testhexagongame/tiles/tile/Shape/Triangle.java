package com.example.testhexagongame.tiles.tile.Shape;

public class Triangle extends Shape2 {
    @Override
    protected void setAdjacentRules() {
        sidesRules.add("left");
        sidesRules.add("right");
        sidesRules.add("base");
    }

    @Override
    protected void setRotationRules() {
        rotations.add(0);
        rotations.add(180);
    }
}
