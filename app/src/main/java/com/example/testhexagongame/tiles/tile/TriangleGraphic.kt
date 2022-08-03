package com.example.testhexagongame.tiles.tile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.testhexagongame.tiles.tile.Shape.Triangle
import com.example.testhexagongame.utils.parseColor

@Composable
fun RenderTriangle2(
    triangle: Box<Triangle, String, String>,
    triangleInsideIcon: @Composable() (BoxScope.() -> Unit)?,
) {
    var colorRender by remember {
        mutableStateOf(triangle.data)
    }
    val onChangeColor: (String) -> Unit = {
            newColor: String -> colorRender = newColor
    }
    triangle.subscribeOnDataChange(onChangeColor)
    if (triangle.data != colorRender) colorRender = triangle.data
    val modifier = if (triangle.rotation == 180 ) Modifier
        .rotate(180F)
        .clip(CustomTriangleShape())
        .background(Color(parseColor(colorRender)))
        .size(35.dp)
    else Modifier
        .clip(CustomTriangleShape())
        .background(Color(parseColor(colorRender)))
        .size(35.dp)
    Box(modifier) {
        if (triangleInsideIcon != null)
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(10.dp))
                Box(modifier = if (triangle.rotation == 180) Modifier.rotate(180F) else Modifier) {
                    triangleInsideIcon()
                }
            }
    }
}

@Composable
@Preview
fun Preview() {
    Box(
        modifier = Modifier
            .size(100.dp)
            .rotate(180F)
            .clip(CustomTriangleShape())
            .background(Color.Red)
            .clickable { println() }
    )
}

class CustomTriangleShape : Shape {

    override fun createOutline(
        size: androidx.compose.ui.geometry.Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(size.width / 2f, 0f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
        return Outline.Generic(path)
    }
}
