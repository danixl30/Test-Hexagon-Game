package com.example.testhexagongame.piece

import com.example.testhexagongame.Color.ColorGenerator
import com.example.testhexagongame.tiles.tile.Box
import com.example.testhexagongame.tiles.tile.Shape.TriangleShape
import com.example.testhexagongame.ui.theme.GRAY_BASE
import com.example.testhexagongame.ui.theme.TRANSPARENT

class Piece2(
    private val options: ArrayList<ArrayList<Int>>,
    private val getRandom: (Int, Int) -> Int,
    private val colorGen: ColorGenerator
): PieceFlippable<TriangleShape> {

    private var triangle: Box<TriangleShape>? = null

    init {
        create()
        for (i in 1..getRandom(1, 6)) flip()
    }

    fun getTriangles(): Box<TriangleShape>? {
        return triangle
    }

    private fun setTriangleRow(
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
        val res = setTriangleRow(tops, triangle2, current+1, last, invertedType)
        triangle2.setAdjacent("right", res.first)
        res.second.add(0, if (!invertedType) triangle1 else triangle2)
        return Pair(triangle1, res.second)
    }

    private fun setTriangleCol() {
        val temp = setTriangleRow(mutableListOf(), null, 1, 2 , false)
        setTriangleRow(temp.second, null, 1, 2, true)
        triangle = temp.first
    }

    override fun create() {
        val color = colorGen.getColorRandom()
        val option = options[getRandom(0, options.size-1)]
        setTriangleCol()
        val triangles = triangle!!.getByRoute(mutableListOf("right", "right", "base", "left", "left"))
        for (i in triangles.indices) {
            if (option[i] == 0) {
                triangles[i].setColor(TRANSPARENT)
                continue
            }
            triangles[i].setColor(color)
        }
    }
    override fun flip() {
        var color = ""
        val triangles = triangle!!.getByRoute(mutableListOf("right", "right", "base", "left", "left"))
        for (triangleItem in triangles) {
            val temp = triangleItem.getColor()
            if (color != "") triangleItem.setColor(color)
            color = temp
        }
        triangles[0].setColor(color)
    }

    private fun checkSingle(triangle1: Box<TriangleShape>?, triangle2: Box<TriangleShape>): Boolean {
        if (triangle2.getColor() == TRANSPARENT) return true
        if (triangle1 != null && triangle1.getColor() == GRAY_BASE) return true
        return false
    }

    private fun validatePath(current: Box<TriangleShape>, piece: Box<TriangleShape>, path: MutableList<String>): Triple<Boolean, MutableList<Box<TriangleShape>?>, List<Box<TriangleShape>>> {
        val path1 = mutableListOf<String>()
        path1.addAll(path)
        val trianglesPiece = piece.getByRoute(path1)
        path1.addAll(path)
        val trianglesBoard = current.getByRoute(path1).toMutableList()
        val pieceOptBoard = trianglesBoard.map { e -> e as Box<TriangleShape>? }.toMutableList()
        if (pieceOptBoard.size < 6) {
            for (i in 1.. (6 - pieceOptBoard.size))
                pieceOptBoard += null
        }
        for (i in trianglesPiece.indices) {
            val res = checkSingle(pieceOptBoard[i], trianglesPiece[i])
            if (!res) return Triple(false, pieceOptBoard, trianglesPiece)
        }
        return Triple(true, pieceOptBoard, trianglesPiece)
    }

    private fun checkAll(current: Box<TriangleShape>?, piece: Box<TriangleShape>?): Boolean {
        if (current?.getRotation() != piece?.getRotation()) return false
        val route1 = mutableListOf("right", "base", "left", "left", "base")
        val route2 = mutableListOf("left", "base", "right", "right", "base")
        val res1 = validatePath(current!!, piece!!, route1)
        if (res1.first) {
            colorAll(res1.second, res1.third)
            return true
        }
        val res2 = validatePath(current, piece, route2)
        if (res2.first) {
            colorAll(res2.second, res2.third)
            return true
        }
        return false
    }

    private fun colorSingle(current: Box<TriangleShape>?, piece: Box<TriangleShape>) {
        if (piece.getColor() == TRANSPARENT) return
        piece.getColor().let { current?.setColor(it) }
    }

    private fun colorAll(trianglesBoard: MutableList<Box<TriangleShape>?>, trianglesPiece: List<Box<TriangleShape>>?) {
        for (i in trianglesPiece!!.indices) {
            colorSingle(trianglesBoard[i], trianglesPiece[i])
        }
    }

    override fun putPiece(current: Box<TriangleShape>?): Boolean {
        return checkAll(current, triangle!!.getAdjacent("right"))
    }
}
