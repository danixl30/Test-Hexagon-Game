package com.example.testhexagongame.piece

import com.example.testhexagongame.tiles.tile.Triangle
import com.example.testhexagongame.ui.theme.BLUE_ITEM
import com.example.testhexagongame.ui.theme.TRANSPARENT

class PieceFactory(
    private val options: ArrayList<ArrayList<Int>>,
    private val getRandom: (Int, Int) -> Int
) {

    private fun setTriangleRow(top: Triangle?, prev: Triangle?, current: Int, last: Int, invertedType: Boolean): Triangle {
        val triangle = Triangle(null, prev, if (invertedType) top else null, invertedType)
        if (current == last && invertedType) {
            top?.base = triangle
        }
        if (current == last) return triangle
        val inverted = Triangle(null, triangle, if (invertedType) null else top, !invertedType)
        if (invertedType) {
            top?.base = triangle
        }else {
            top?.base = inverted
        }
        triangle.right = inverted
        val currentTop = top?.right?.right as Triangle?
        inverted.right = setTriangleRow(currentTop, inverted, current+1, last, invertedType)
        return triangle
    }
    private fun setTriangleCol(): Triangle {
        var first: Triangle? = null
        var temp: Triangle? = null
        temp = setTriangleRow(temp, null, 1, 2 , false)
        first = temp
        setTriangleRow(temp, null, 1, 2, true)
        return first
    }
    private fun getOption(): ArrayList<Int> {
        val index = getRandom(0, options.size-1)
        return this.options[index]
    }

    fun create(): Piece {
        val triangle = setTriangleCol()
        val option = getOption()
        println(option)
        var temp = triangle as Triangle?
        var index = 0
        for (i in 1..3) {
            val colorNum = option[index]
            index++
            temp?.setColor(if (colorNum == 1) BLUE_ITEM else TRANSPARENT)
            if (temp?.right != null) temp = temp.right as Triangle?
        }
        temp = temp?.base as Triangle?
        for (i in 1..3) {
            val colorNum = option[index]
            index++
            temp?.setColor(if (colorNum == 1) BLUE_ITEM else TRANSPARENT)
            if (temp?.left != null) temp = temp.left as Triangle?
        }
        return Piece(triangle)
    }
}