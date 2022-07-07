package com.example.testhexagongame.Game

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.testhexagongame.Color.ColorGenerator
import com.example.testhexagongame.R
import com.example.testhexagongame.dragAndDrop.LongPressDraggable
import com.example.testhexagongame.hexagon.center.HexagonCenter2
import com.example.testhexagongame.hexagon.center.HexagonCenterFactory2
import com.example.testhexagongame.piece.Piece2
import com.example.testhexagongame.piece.RenderPiece
import com.example.testhexagongame.tiles.tile.*
import com.example.testhexagongame.tiles.tile.Graphics.RenderRow2
import com.example.testhexagongame.tiles.tile.Shape.TriangleShape
import com.example.testhexagongame.ui.theme.*
import com.example.testhexagongame.utils.randomNum

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
    val option7 = ArrayList<Int>(6)
    option7.add(1)
    option7.add(0)
    option7.add(1)
    option7.add(0)
    option7.add(0)
    option7.add(0)
    val res = ArrayList<ArrayList<Int>>()
    res += option2
    res += option3
    res += option4
    res += option5
    res += option6
    res.add(option7)
    return res
}

val listColors = listOf(
    BLUE_ITEM,
    YELLOW_ITEM,
    GREEN_ITEM,
    RED_ITEM,
    PURPLE_ITEM,
    CELESTA_ITEM,
    ORANGE_ITEM
)

@Composable
fun GameScreen2(navController: NavHostController) {
    val colorGen = ColorGenerator(listColors, randomNum)
    val triangles by remember {
        mutableStateOf(
            BoardFactory().create()
        )
    }
    val listPieces = remember {
        mutableStateListOf<Piece2>()
    }
    
    var stateText by remember {
        mutableStateOf("")
    }
    
    var enabledHammer by remember {
        mutableStateOf(false)
    }

    var enabledTrash by remember {
        mutableStateOf(false)
    }
    
    fun setEnableHammer() {
        if (enabledHammer) {
            enabledHammer = false
            stateText = ""
            return
        }
        enabledHammer = true
        enabledTrash = false
        stateText = "Hammer"
    }

    fun setEnableTrash() {
        if (enabledTrash) {
            enabledTrash = false
            stateText = ""
            return
        }
        enabledHammer = false
        enabledTrash = true
        stateText = "Trash"
    }

    fun goToHome() {
        navController.popBackStack()
        navController.navigate("main")
    }
    
    fun deleteColor(triangle: Box<TriangleShape>) {
        if (triangle.getColor() == GRAY_BASE) return
        triangle.setColor(GRAY_BASE)
        enabledHammer = false
        stateText = ""
    }

    while (listPieces.size < 3)
        listPieces.add(Piece2(setOptions(), randomNum, colorGen))

    val removePiece = { piece: Piece2 ->
        run {
            listPieces.remove(piece)
            if (listPieces.size < 3) listPieces.add(Piece2(setOptions(), randomNum, colorGen))
            if (enabledTrash) {
                enabledTrash = false
                stateText = ""
            }
            return@run
        }
    }
    val centers by remember {
        mutableStateOf(HexagonCenterFactory2(triangles).create())
    }
    fun onChangeColor() {
        val listCenters = arrayListOf<HexagonCenter2>()
        for (center in centers) {
            val result = center.check()
            if (result) listCenters.add(center)
        }
        listCenters.forEach { hexagonCenter -> hexagonCenter.empty() }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
                onClick = { goToHome() },
                shape = CircleShape,
                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = MaterialTheme.colors.onBackground),
            ) {
                Icon(Icons.Default.Home, contentDescription = "Home", tint = MaterialTheme.colors.onSecondary)
            }
            Row() {
                OutlinedButton(
                    onClick = { setEnableTrash() },
                    shape = CircleShape,
                    colors = ButtonDefaults.outlinedButtonColors(backgroundColor = MaterialTheme.colors.onBackground),
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Trash", tint = MaterialTheme.colors.onSecondary)
                }
                Spacer(modifier = Modifier.width(20.dp))
                OutlinedButton(
                    onClick = { setEnableHammer() },
                    shape = CircleShape,
                    colors = ButtonDefaults.outlinedButtonColors(backgroundColor = MaterialTheme.colors.onBackground),
                ) {
                    Icon(painterResource(R.drawable.hammer), contentDescription = "Hammer", tint = MaterialTheme.colors.onSecondary)
                }
            }
        }
        if (stateText.isNotEmpty()) {
            Spacer(modifier = Modifier.height(30.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = stateText, fontSize = 40.sp)
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
        LongPressDraggable(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val itemsTiles = BoardCollectionGenerator(triangles).createCollection()
                while (itemsTiles.hasNext()) {
                    Row() {
                        RenderRow2(
                            triangles = itemsTiles.getNext(), 
                            removePiece = removePiece, 
                            onChangeColor = { onChangeColor() },
                            onClickItem = if (enabledHammer) ::deleteColor else null
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                }
                Spacer(modifier = Modifier.height(30.dp))
                LazyRow(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                    items(listPieces) { piece ->
                        RenderPiece(piece = piece, listPieces.indexOfFirst { it == piece }, deletePiece = if (enabledTrash) removePiece else null)
                    }
                }
            }
        }
    }
}