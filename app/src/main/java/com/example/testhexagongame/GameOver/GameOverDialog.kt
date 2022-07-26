package com.example.testhexagongame.GameOver

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.testhexagongame.ui.theme.Shapes
import com.example.testhexagongame.utils.parseColor

@Composable
fun GameOverDialog(onExit: () -> Unit) {
    AlertDialog(onDismissRequest = { onExit() },
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth().background(Color(parseColor("#0075D2")))
            ) {
                Text(text = "Game over...", fontSize = 45.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        },
        backgroundColor = MaterialTheme.colors.primary,
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "You don't have moves...",
                    color = MaterialTheme.colors.onPrimary,
                    fontSize = 20.sp)
            }
        },
        confirmButton = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { onExit() },
                    shape = CircleShape,
                    colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color(parseColor("#0075D2"))),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Ok", fontSize = 25.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    )
}

@Composable
@Preview
fun preview() {
    GameOverDialog {
        println("")
    }
}