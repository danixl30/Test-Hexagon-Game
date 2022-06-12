package com.example.testhexagongame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testhexagongame.hexagon.center.HexagonCenter
import com.example.testhexagongame.hexagon.center.HexagonCenterFactory
import com.example.testhexagongame.tiles.tile.TilesFactory
import com.example.testhexagongame.tiles.tile.Triangle
import com.example.testhexagongame.ui.theme.TestHexagonGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RenderHexagon()
        }
    }
}

@Composable
fun RenderHexagon() {
    val triangles = TilesFactory().create()
    var temp = triangles as Triangle?
    val centers = HexagonCenterFactory(triangles).create()
    fun onChangeColor() {
        val listCenters = arrayListOf<HexagonCenter>()
        for (center in centers) {
            val result = center.check()
            if (result) listCenters.add(center)
        }
        listCenters.forEach { hexagonCenter -> hexagonCenter.empty() }
    }
    TestHexagonGameTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            for (i in 1..3) {
                Row() {
                    RenderRow(triangle = temp) { onChangeColor() }
                }
                Spacer(modifier = Modifier.height(14.dp))
                if (temp?.base?.left == null) {
                    temp = temp?.base as Triangle?
                    break
                }
                temp = temp?.base?.left as Triangle?
            }
            for (i in 1..3) {
                Row() {
                    RenderRow(triangle = temp) { onChangeColor() }
                }
                Spacer(modifier = Modifier.height(14.dp))
                temp = temp?.right?.base as Triangle?
            }
        }
    }
}

@Composable
fun RenderRow(triangle: Triangle?, onChangeColor: () -> Unit) {
    triangle?.GraphicItem(onChangeColor)
    if (triangle?.right != null) RenderRow(
        triangle = triangle.right as Triangle?,
        onChangeColor = onChangeColor
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RenderHexagon()
}