package com.example.testhexagongame.hexagon.center

import androidx.compose.ui.graphics.Color
import com.example.testhexagongame.tiles.tile.Triangle

class HexagonCenter: Region {

    private var triangles: ArrayList<Triangle?> = ArrayList()

    override fun check(): Boolean {
        val color = triangles[0]?.getColor()
        if (color == "#616161") return false
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
        triangles.forEach { triangle -> triangle?.setColor("#616161") }
    }
}