package com.example.testhexagongame.Routes

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testhexagongame.Game.GameScreen
import com.example.testhexagongame.Main.MainScreen
import com.example.testhexagongame.Splash.SplashScreen

@Composable
fun RouterManager() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(navController)
        }
        composable("main") {
            MainScreen(navController)
        }
        composable("game") {
            GameScreen(navController)
        }
    }
}
