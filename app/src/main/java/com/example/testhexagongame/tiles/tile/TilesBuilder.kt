package com.example.testhexagongame.tiles.tile

class TilesFactory {

    private fun setTriangleRow(top: Triangle?, prev: Triangle?, current: Int, last: Int, invertedType: Boolean): Triangle {
        val triangle = Triangle(null, prev, if (invertedType) top else null, invertedType)
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
         for (i in 3..5) {
            temp = setTriangleRow(temp, null, 1, i , false)
            if (first == null) first = temp
         }
         for (i in 5 downTo 3) {
             temp = setTriangleRow(temp, null, 1, i , true)
             temp = temp.right as Triangle?
         }
         return first!!
     }

    fun create(): Triangle {
        return setTriangleCol()
    }
}