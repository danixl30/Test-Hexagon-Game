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
import com.example.testhexagongame.tiles.tile.Shape.Triangle
import com.example.testhexagongame.utils.parseColor

@Composable
fun RenderTriangle2(triangle: Box2<Triangle, String>) {
    var colorRender by remember {
        mutableStateOf(triangle.data)
    }
    val onChangeColor: (String) -> Unit = {
            newColor: String -> colorRender = newColor
    }
    triangle.subscribeOnDataChange(onChangeColor)
    if (triangle.data != colorRender) colorRender = triangle.data
    Box() {
        Canvas(
            modifier = if (triangle.rotation == 0) Modifier.size(35.dp)
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
fun Path.moveTo(offset: Offset) = moveTo(offset.x, offset.y)
fun Path.lineTo(offset: Offset) = lineTo(offset.x, offset.y)
