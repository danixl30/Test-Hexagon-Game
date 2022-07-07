package com.example.testhexagongame.hexagon.center

import com.example.testhexagongame.tiles.tile.Box
import com.example.testhexagongame.tiles.tile.Shape.Shape
import com.example.testhexagongame.ui.theme.GRAY_BASE

class HexagonCenter2: Region {

    private var triangles: ArrayList<Box<Shape>> = ArrayList()

    override fun check(): Boolean {
        val color = triangles[0].getColor()
        if (color == GRAY_BASE) return false
        for (triangle in triangles) {
            if (triangle.getColor() != color) return false
        }
        return true
    }

    fun addTriangle(triangle: Box<Shape>) {
        if (triangles.size == 6) return
        triangles.add(triangle)
    }

    override fun empty() {
        triangles.forEach { triangle -> triangle.setColor(GRAY_BASE) }
    }
}
