package com.example.testhexagongame.tiles.tile

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.dp
import com.example.testhexagongame.tiles.tile.Shape.TriangleShape
import com.example.testhexagongame.utils.parseColor

@Composable
fun RenderTriangle(triangle: Box<TriangleShape>) {
    var colorRender by remember {
        mutableStateOf(triangle.getColor())
    }
    val onChangeColor: (String) -> Unit = {
            newColor: String -> colorRender = newColor
    }
    triangle.subscribeColorChange(onChangeColor)
    if (triangle.getColor() != colorRender) colorRender = triangle.getColor()
    Box() {
        Canvas(
            modifier = if (triangle.getRotation() == 0) Modifier.size(35.dp)
            else Modifier
                .size(35.dp)
                .rotate(180F)
        ) {
            val rect = Rect(Offset.Zero, size)
            val trianglePath = Path().apply {
                moveTo(rect.topCenter)
                lineTo(rect.bottomRight)
                lineTo(rect.bottomLeft)
                close()
            }
            drawIntoCanvas { canvas ->
                canvas.drawOutline(
                    outline = Outline.Generic(trianglePath),
                    paint = Paint().apply {
                        color = Color(parseColor(colorRender))
                        pathEffect = PathEffect.cornerPathEffect(rect.maxDimension / 5)
                    }
                )
            }
        }
    }
}