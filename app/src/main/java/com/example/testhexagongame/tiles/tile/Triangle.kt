package com.example.testhexagongame.tiles.tile

import androidx.annotation.ColorInt
import androidx.annotation.Size
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.dp
import com.example.testhexagongame.ui.theme.BLUE_ITEM
import com.example.testhexagongame.ui.theme.GRAY_BASE
import com.example.testhexagongame.utils.Observer

class Triangle(
    override var right: TileModel?,
    override var left: TileModel?,
    override var base: TileModel?,
    override var isFlipped: Boolean,
): TileModel {

    private val color = Observer<String>(GRAY_BASE)

    fun getColor(): String {
        return color.getValue()
    }

    fun setColor(colorData: String) {
       color.setValue(colorData)
    }

    @Composable
    override fun GraphicItem(onChage: (() -> Unit)?) {
        val colorRender = remember {
            mutableStateOf(Color(parseColor(color.getValue())))
        }
        val onChangeColor: (String) -> Unit = {
            newColor: String -> colorRender.value = Color(parseColor(newColor))
        }
        color.subscribe ( onChangeColor )
        Box(
            modifier = if (onChage != null)
                Modifier.clickable {
                color.setValue(BLUE_ITEM)
                colorRender.value = Color(parseColor(color.getValue()))
                onChage()
            } else Modifier.focusable()
        ) {
            Canvas(
                modifier = if (!isFlipped) Modifier.size(35.dp)
                else Modifier
                    .size(35.dp)
                    .rotate(180F)
            ) {
                val rect = Rect(Offset.Zero, size)
                val trianglePath = Path().apply {
/*
                    val height = size.height
                    val width = size.width
*/
                    moveTo(rect.topCenter)
                    lineTo(rect.bottomRight)
                    lineTo(rect.bottomLeft)
                    close()
/*
                    moveTo((width) / 2.0f, 0f)
                    lineTo(width, height  )
                    lineTo(0f, height)
*/
                }
                drawIntoCanvas { canvas ->
                    canvas.drawOutline(
                        outline = Outline.Generic(trianglePath),
                        paint = Paint().apply {
                            color = colorRender.value
                            pathEffect = PathEffect.cornerPathEffect(rect.maxDimension / 5)
                        }
                    )
                }
/*
                drawPath(
                    trianglePath,
                    colorRender.value,
                    style = Stroke(
                        width = 2.dp.toPx(),
                        pathEffect = PathEffect.cornerPathEffect(4.dp.toPx())
                    )
                )
*/
            }
        }
    }
}

fun Path.moveTo(offset: Offset) = moveTo(offset.x, offset.y)
fun Path.lineTo(offset: Offset) = lineTo(offset.x, offset.y)

@ColorInt
fun parseColor(@Size(min = 1) colorString: String): Int {
    if (colorString[0] == '#') { // Use a long to avoid rollovers on #ffXXXXXX
        var color = colorString.substring(1).toLong(16)
        if (colorString.length == 7) { // Set the alpha value
            color = color or -0x1000000
        } else require(colorString.length == 9) { "Unknown color" }
        return color.toInt()
    }
    throw IllegalArgumentException("Unknown color")
}