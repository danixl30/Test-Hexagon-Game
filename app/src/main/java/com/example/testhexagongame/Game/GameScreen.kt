package com.example.testhexagongame.Game

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.testhexagongame.Color.ColorGenerator
import com.example.testhexagongame.GameOver.GameOverDialog
import com.example.testhexagongame.Points.PointsManager
import com.example.testhexagongame.Points.PointsPolity
import com.example.testhexagongame.R
import com.example.testhexagongame.dragAndDrop.LongPressDraggable
import com.example.testhexagongame.hammer.Hammer
import com.example.testhexagongame.piece.*
import com.example.testhexagongame.tiles.tile.*
import com.example.testhexagongame.tiles.tile.Graphics.RenderRow
import com.example.testhexagongame.tiles.tile.Shape.Triangle
import com.example.testhexagongame.trash.Trash
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
fun GameScreen(navController: NavHostController) {
    val colorGen = ColorGenerator(ArrayList(listColors), randomNum)
    val pointsManager = PointsManager(PointsPolity())
    val pieceManagerBoard = PieceManagerBoard(
        colorGen,
        randomNum,
        setOptions(),
        PieceColorChecker(),
        ColorTransfer()
    )
    val game by remember {
        mutableStateOf(Game(
            pointsManager,
            pieceManagerBoard
        ))
    }
    val context = LocalContext.current
    val hammer by remember {
        mutableStateOf(Hammer<Triangle, String, String>(pointsManager, GRAY_BASE) { Toast.makeText(context, it, Toast.LENGTH_LONG).show() })
    }
    val trash by remember {
        mutableStateOf(Trash(pointsManager, pieceManagerBoard) { Toast.makeText(context, it, Toast.LENGTH_LONG).show() })
    }
    val listPieces = remember {
        mutableStateListOf<HexagonPiece>()
    }
    if (listPieces.size == 0) {
        game.pieces.forEach { e -> listPieces.add(e) }
    }

    var enabledHammer by remember {
        mutableStateOf(false)
    }

    var enabledTrash by remember {
        mutableStateOf(false)
    }

    var onGameOver by remember {
        mutableStateOf(false)
    }

    var points by remember {
        mutableStateOf(0)
    }

    var hammerCost by remember {
        mutableStateOf(100)
    }

    var trashCost by remember {
        mutableStateOf(50)
    }

    hammer.subscribeOnEnable {
        println(it)
        enabledHammer = it }
    trash.subscribeOnEnable { enabledTrash = it }
    game.subscribeOnChangePieces {
        run {
            listPieces.clear()
            it.forEach { listPieces += it }
        }
    }

    game.subscribeOnGameOver { onGameOver = it }
    game.subscribeOnPointsChange { points = it }
    hammer.subscribeOnCostChange { hammerCost = it }
    trash.subscribeOnCostChange { trashCost = it }

    fun setEnableHammer() {
        hammer.onEnable()
    }

    fun setEnableTrash() {
        trash.onEnable()
    }

    fun goToHome() {
        navController.popBackStack()
        navController.navigate("main")
    }

    fun deleteColor(triangle: Box<Triangle, String, String>) {
        hammer.destroy(triangle)
    }

    val putPiece = { piece: HexagonPiece ->
        run {
            game.onPutPiece(piece)
        }
    }

    fun removePiece(piece: HexagonPiece) {
        trash.delete(piece)
    }

    if (onGameOver) {
        GameOverDialog {
            goToHome()
        }
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
            Row {
                OutlinedButton(
                    onClick = { setEnableTrash() },
                    shape = CircleShape,
                    colors = ButtonDefaults.outlinedButtonColors(backgroundColor = MaterialTheme.colors.onBackground),
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Delete, contentDescription = "Trash", tint = MaterialTheme.colors.onSecondary)
                        Text(
                            text = trashCost.toString(), color = MaterialTheme.colors.onPrimary,
                            fontSize = 10.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.width(20.dp))
                OutlinedButton(
                    onClick = { setEnableHammer() },
                    shape = CircleShape,
                    colors = ButtonDefaults.outlinedButtonColors(backgroundColor = MaterialTheme.colors.onBackground),
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(painterResource(R.drawable.hammer), contentDescription = "Hammer", tint = MaterialTheme.colors.onSecondary)
                        Text(
                            text = hammerCost.toString(), color = MaterialTheme.colors.onPrimary,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }
        Row {
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = points.toString(), color = MaterialTheme.colors.onPrimary,
                fontSize = 40.sp
            )
        }
        if (enabledHammer) {
            Spacer(modifier = Modifier.height(30.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = "Hammer",
                    color = MaterialTheme.colors.onPrimary,
                    fontSize = 40.sp)
            }
        }

        if (enabledTrash) {
            Spacer(modifier = Modifier.height(30.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = "Trash",
                    color = MaterialTheme.colors.onPrimary,
                    fontSize = 40.sp)
            }
        }
        LongPressDraggable(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val itemsTiles = game.boardByIterable
                while (itemsTiles.hasNext()) {
                    Row {
                        RenderRow(
                            triangles = itemsTiles.next,
                            removePiece = putPiece,
                            onClickItem = if (enabledHammer) ::deleteColor else null
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                }
                Spacer(modifier = Modifier.height(30.dp))
                LazyRow(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                    items(listPieces) { piece ->
                        RenderPiece(piece = piece, listPieces.indexOfFirst { it == piece }, deletePiece = if (enabledTrash) ::removePiece else null)
                    }
                }
            }
        }
    }
}
