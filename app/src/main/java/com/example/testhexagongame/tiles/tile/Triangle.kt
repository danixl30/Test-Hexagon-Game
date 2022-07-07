package com.example.testhexagongame.tiles.tile

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import com.example.testhexagongame.tiles.tile.Shape.Shape
import com.example.testhexagongame.ui.theme.GRAY_BASE
import com.example.testhexagongame.utils.Observer

class Box<T: Shape>(
    private val shape: T
) {

    private val color = Observer(GRAY_BASE)

    fun getColor(): String {
        return color.getValue()
    }

    fun subscribeColorChange(callback: (String) -> Unit) {
        color.subscribe(callback)
    }

    fun setColor(colorData: String) {
        color.setValue(colorData)
    }

    fun setAdjacent(side: String, box: Box<T>) {
        shape.addAdjacent(side, box as Box<Shape>)
    }

    fun getAdjacent(side: String): Box<T>? {
        return shape.getAdjacent(side) as Box<T>?
    }

    fun setRotation(value: Int) {
        shape.setRotation(value)
    }

    fun getByRoute(paths: MutableList<String>): List<Box<T>> {
        if (paths.size == 0) return listOf(this)
        val next = paths[0]
        paths.removeFirst()
        if (this.getAdjacent(next) == null) return listOf(this)
        return listOf(this) + this.getAdjacent(next)!!.getByRoute(paths)
    }

    fun getRotation(): Int {
        return shape.getRotation()
    }
}

fun Path.moveTo(offset: Offset) = moveTo(offset.x, offset.y)
fun Path.lineTo(offset: Offset) = lineTo(offset.x, offset.y)
