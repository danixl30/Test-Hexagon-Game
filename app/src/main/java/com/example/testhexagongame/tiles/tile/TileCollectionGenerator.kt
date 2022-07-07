package com.example.testhexagongame.tiles.tile

import com.example.testhexagongame.tiles.tile.Shape.TriangleShape
import com.example.testhexagongame.utils.Iterator

class BoardCollectionGenerator(private val triangle: Box<TriangleShape>?) {
    private fun setRow(triangle: Box<TriangleShape>?): Iterator<Box<TriangleShape>> {
        var temp = triangle
        val list = mutableListOf<Box<TriangleShape>>()
        while (temp != null) {
            list.add(temp)
            if (temp.getAdjacent("right") == null) break
            temp = temp.getAdjacent("right")
        }
        return Iterator(list)
    }
    private fun setRow(): Iterator<Iterator<Box<TriangleShape>>> {
        var temp = triangle
        val inters = mutableListOf<Iterator<Box<TriangleShape>>>()
        for (i in 1..3) {
            val inter = setRow(temp)
            inters.add(inter)
            if (temp?.getAdjacent("base")?.getAdjacent("left") == null) {
                temp = temp?.getAdjacent("base")
                break
            }
            temp = temp.getAdjacent("base")?.getAdjacent("left")
        }
        for (i in 1..3) {
            val inter = setRow(temp)
            inters.add(inter)
            temp = temp?.getAdjacent("right")?.getAdjacent("base")
        }
        return Iterator(inters)
    }
    fun createCollection(): Iterator<Iterator<Box<TriangleShape>>> {
        return setRow()
    }
}
