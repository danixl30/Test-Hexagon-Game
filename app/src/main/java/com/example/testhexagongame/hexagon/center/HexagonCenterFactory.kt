package com.example.testhexagongame.hexagon.center

import com.example.testhexagongame.Factories.Factory
import com.example.testhexagongame.tiles.tile.Box
import com.example.testhexagongame.tiles.tile.Shape.Shape
import com.example.testhexagongame.tiles.tile.Shape.TriangleShape

class HexagonCenterFactory2(private val triangle: Box<TriangleShape>?): Factory<ArrayList<HexagonCenter2>> {

    private var hexagonCenters: ArrayList<HexagonCenter2> = ArrayList()

    private fun checkRow(triangle: Box<TriangleShape>?) {
        if (triangle?.getAdjacent("right") == null) return
        val center = HexagonCenter2()
        val triangles = triangle.getByRoute(mutableListOf("right", "right", "base", "left", "left"))
        if (triangles.size < 6) return
        triangles.forEach { e -> center.addTriangle(e as Box<Shape>) }
        hexagonCenters.add(center)
        checkRow(triangle.getAdjacent("right")?.getAdjacent("right"))
    }

    private fun searchCol() {
        var temp = triangle
        for (i in 1..3) {
            checkRow(temp)
            if (temp?.getAdjacent("base")?.getAdjacent("left") == null) {
                temp = temp?.getAdjacent("base")
                break
            }
            temp = temp.getAdjacent("base")?.getAdjacent("left")
        }
        for (i in 1..3) {
            checkRow(temp?.getAdjacent("right"))
            temp = temp?.getAdjacent("right")?.getAdjacent("base")
        }
    }

    override fun create(): ArrayList<HexagonCenter2> {
        searchCol()
        return hexagonCenters
    }
}
