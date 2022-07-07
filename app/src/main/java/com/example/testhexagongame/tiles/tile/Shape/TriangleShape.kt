package com.example.testhexagongame.tiles.tile.Shape

class TriangleShape: Shape() {
    override fun setAdjacent() {
        sides.add("left")
        sides.add("right")
        sides.add("base")
    }

    override fun setRotationRules() {
        this.rotations.add(0)
        this.rotations.add(180)
    }
}