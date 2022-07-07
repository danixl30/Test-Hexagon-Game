package com.example.testhexagongame.tiles.tile

import com.example.testhexagongame.Factories.Factory
import com.example.testhexagongame.tiles.tile.Shape.TriangleShape

class BoardFactory: Factory<Box<TriangleShape>> {
    private fun createRow(
        tops: MutableList<Box<TriangleShape>>,
        prev: Box<TriangleShape>?,
        current: Int,
        last: Int,
        invertedType: Boolean
    ): Pair<Box<TriangleShape>, MutableList<Box<TriangleShape>>> {
        val triangle1 = Box(TriangleShape())
        prev?.let { triangle1.setAdjacent("left", it) }
        triangle1.setRotation(if (!invertedType) 0 else 180)
        if (invertedType && tops.size > 0) {
            val top = tops[0]
            tops.removeFirst()
            top.let { triangle1.setAdjacent("base", it) }
            top.setAdjacent("base", triangle1)
        }
        if (current == last && !invertedType) return Pair(triangle1, mutableListOf(triangle1))
        if (current == last && invertedType) return Pair(triangle1, mutableListOf())
        val triangle2 = Box(TriangleShape())
        triangle2.setAdjacent("left", triangle1)
        triangle1.setAdjacent("right", triangle2)
        triangle2.setRotation(if (invertedType) 0 else 180)
        if (!invertedType && tops.size > 0) {
            val top = tops[0]
            tops.removeFirst()
            top.setAdjacent("base", triangle2)
            top.let { triangle2.setAdjacent("base", it) }
        }
        val res = createRow(tops, triangle2, current+1, last, invertedType)
        triangle2.setAdjacent("right", res.first)
        res.second.add(0, if (!invertedType) triangle1 else triangle2)
        return Pair(triangle1, res.second)
    }

    private fun setTriangleCol(): Box<TriangleShape> {
        var first: Box<TriangleShape>? = null
        var temp: Pair<Box<TriangleShape>, MutableList<Box<TriangleShape>>> = Pair(Box(TriangleShape()), mutableListOf())
        for (i in 3..5) {
            temp = createRow(temp.second, null, 1, i , false)
            if (first == null) first = temp.first
        }
        for (i in 5 downTo 3) {
            temp = createRow(temp.second, null, 1, i , true)
        }
        return first!!
    }

    override fun create(): Box<TriangleShape> {
        return setTriangleCol()
    }
}
