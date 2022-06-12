package com.example.testhexagongame.tiles.tile

import androidx.annotation.ColorInt
import androidx.annotation.Size
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import com.example.testhexagongame.utils.Observer

class Triangle(
    override var right: TileModel?,
    override var left: TileModel?,
    override var base: TileModel?,
    override var isFlipped: Boolean,
): TileModel {

    private val color = Observer<String>("#616161")

    fun getColor(): String {
        return color.getValue()
    }

    fun setColor(colorData: String) {
       color.setValue(colorData)
    }

    @Composable
    override fun GraphicItem(onChage: () -> Unit) {
        val colorRender = remember {
            mutableStateOf(Color(parseColor(color.getValue())))
        }
        val onChangeColor: (String) -> Unit = {
            newColor: String -> colorRender.value = Color(parseColor(newColor))
        }
        color.subscribe ( onChangeColor )
        Box(
            Modifier.clickable {
                color.setValue("#076ee3")
                colorRender.value = Color(parseColor(color.getValue()))
                onChage()
            }
        ) {
            Canvas(
                modifier = if (!isFlipped) Modifier.size(40.dp)
                else Modifier
                    .size(40.dp)
                    .rotate(180F)
            ) {
                val trianglePath = Path().apply {
                    val height = size.height
                    val width = size.width
                    moveTo((width) / 2.0f, 0f)
                    lineTo(width, height  )
                    lineTo(0f, height)
                }
                drawPath(
                    trianglePath,
                    colorRender.value
                )
            }
        }
    }
}

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