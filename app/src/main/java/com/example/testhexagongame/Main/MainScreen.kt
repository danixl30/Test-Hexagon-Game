package com.example.testhexagongame.Main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun MainScreen(navController: NavController) {
    fun onClickPlay() {
        navController.popBackStack()
        navController.navigate("game")
    }
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Make Hexa Game \"Clone\"",
            fontSize = 50.sp,
            color = MaterialTheme.colors.onPrimary,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(80.dp))
        OutlinedButton(
            onClick = { onClickPlay() },
            modifier = Modifier.size(140.dp),
            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Blue),
            shape = CircleShape
        ) {
            Icon(Icons.Default.PlayArrow, contentDescription = "Play", modifier = Modifier.size(80.dp), tint = Color.White)
        }
    }
}