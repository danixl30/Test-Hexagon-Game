package com.example.testhexagongame.hexagon.center

import com.example.testhexagongame.tiles.tile.Triangle

class HexagonCenterFactory(private val triangle: Triangle) {

    private var hexagonCenters: ArrayList<HexagonCenter> = ArrayList()

    private fun checkRow(triangle: Triangle?) {
        if (triangle == null) return
        val center = HexagonCenter()
        center.addTriangle(triangle)
        center.addTriangle(triangle.right as Triangle?)
        center.addTriangle(triangle.right?.base as Triangle?)
        center.addTriangle(triangle.right?.base?.left as Triangle?)
        center.addTriangle(triangle.left as Triangle?)
        center.addTriangle(triangle.left?.base as Triangle?)
        hexagonCenters.add(center)
        checkRow(triangle.right?.right as Triangle?)
    }

    private fun searchCol() {
        var temp = triangle as Triangle?
        for (i in 1..3) {
            checkRow(temp?.right as Triangle?)
            if (temp?.base?.left == null) {
                temp = temp?.base as Triangle?
                break
            }
            temp = temp.base?.left as Triangle?
        }
        temp = temp?.right as Triangle?
        for (i in 1..3) {
            if (temp?.left != null) {
                temp = temp.left as Triangle?
            }
            checkRow(temp?.right as Triangle?)
            temp = temp?.right?.base as Triangle?
        }
    }

    fun create(): ArrayList<HexagonCenter> {
        searchCol()
        return hexagonCenters
    }
}