package com.example.testhexagongame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testhexagongame.dragAndDrop.LongPressDraggable
import com.example.testhexagongame.hexagon.center.HexagonCenter
import com.example.testhexagongame.hexagon.center.HexagonCenterFactory
import com.example.testhexagongame.piece.PieceFactory
import com.example.testhexagongame.tiles.tile.TilesFactory
import com.example.testhexagongame.tiles.tile.Triangle
import com.example.testhexagongame.ui.theme.TestHexagonGameTheme
import kotlin.coroutines.suspendCoroutine

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RenderHexagon()
        }
    }
}

fun setOptions(): ArrayList<ArrayList<Int>> {
    val option1 = ArrayList<Int>(6)
    option1.add(1)
    option1.add(0)
    option1.add(0)
    option1.add(0)
    option1.add(0)
    option1.add(0)
    val option2 = ArrayList<Int>(6)
    option2.add(1)
    option2.add(1)
    option2.add(0)
    option2.add(0)
    option2.add(0)
    option2.add(0)
    val option3 = ArrayList<Int>(6)
    option3.add(1)
    option3.add(1)
    option3.add(1)
    option3.add(0)
    option3.add(0)
    option3.add(0)
    val option4 = ArrayList<Int>(6)
    option4.add(1)
    option4.add(1)
    option4.add(1)
    option4.add(1)
    option4.add(0)
    option4.add(0)
    val option5 = ArrayList<Int>(6)
    option5.add(1)
    option5.add(1)
    option5.add(1)
    option5.add(1)
    option5.add(1)
    option5.add(0)
    val option6 = ArrayList<Int>(6)
    option6.add(1)
    option6.add(0)
    option6.add(1)
    option6.add(0)
    option6.add(1)
    option6.add(0)
    val res = ArrayList<ArrayList<Int>>()
    res.add(option1)
    res.add(option2)
    res.add(option3)
    res.add(option4)
    res.add(option5)
    res.add(option6)
    return res
}

val randomNum: (Int, Int) -> Int = { initial: Int, last: Int -> (initial..last).random() }

@Composable
fun RenderHexagon() {
    val triangles = TilesFactory().create()
    var temp = triangles as Triangle?
    val centers = HexagonCenterFactory(triangles).create()
    val piece1 = PieceFactory(setOptions(), getRandom = randomNum).create()
    val piece2 = PieceFactory(setOptions(), getRandom = randomNum).create()
    val piece3 = PieceFactory(setOptions(), getRandom = randomNum).create()
    fun onChangeColor() {
        val listCenters = arrayListOf<HexagonCenter>()
        for (center in centers) {
            val result = center.check()
            if (result) listCenters.add(center)
        }
        listCenters.forEach { hexagonCenter -> hexagonCenter.empty() }
    }
    TestHexagonGameTheme {
        LongPressDraggable(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
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
                Spacer(modifier = Modifier.height(40.dp))
                Row {
                    piece1.GraphicComponent()
                    Spacer(modifier = Modifier.width(20.dp))
                    piece2.GraphicComponent()
                    Spacer(modifier = Modifier.width(20.dp))
                    piece3.GraphicComponent()
                }
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