package com.example.testhexagongame.hexagon.center

import androidx.compose.ui.graphics.Color
import com.example.testhexagongame.tiles.tile.Triangle
import com.example.testhexagongame.ui.theme.GRAY_BASE

class HexagonCenter: Region {

    private var triangles: ArrayList<Triangle?> = ArrayList()

    override fun check(): Boolean {
        val color = triangles[0]?.getColor()
        if (color == GRAY_BASE) return false
        for (triangle in triangles) {
            if (triangle?.getColor() != color) return false
        }
        return true
    }

    fun addTriangle(triangle: Triangle?) {
        if (triangles.size == 6) return
        triangles.add(triangle)
    }

    override fun empty() {
        triangles.forEach { triangle -> triangle?.setColor(GRAY_BASE) }
    }
}