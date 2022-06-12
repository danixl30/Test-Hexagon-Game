package com.example.testhexagongame.tiles.tile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.intellij.lang.annotations.JdkConstants

class Piece(private val triangle: Triangle) {
    fun flip() {
        var color = ""
        var triangleTemp = triangle
        for (i in 1..3) {
            val temp2 = triangleTemp.getColor()
            if (color.isNotEmpty()) triangleTemp.setColor(color)
            color = temp2
            if (triangleTemp.right != null) triangleTemp = triangleTemp.right as Triangle
        }
        triangleTemp = triangleTemp.base as Triangle
        for (i in 1..3) {
            val temp2 = triangleTemp.getColor()
            if (color.isNotEmpty()) triangleTemp.setColor(color)
            color = temp2
            if (triangleTemp.left != null) triangleTemp = triangleTemp.left as Triangle
        }
        triangle.setColor(color)
    }

    @Composable
    fun GraphicComponent() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable { flip() }
        ) {
            Row() {
                triangle.GraphicItem {}
                triangle.right?.GraphicItem {}
                triangle.right?.right?.GraphicItem {}
            }
            Row() {
                triangle.base?.GraphicItem {}
                triangle.base?.right?.GraphicItem {}
                triangle.base?.right?.right?.GraphicItem {}
            }

        }
    }
}