package com.example.worldskills.nav

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.worldskills.views.Auth
import com.example.worldskills.views.OnBoardingScreen
import com.example.worldskills.views.SplashScreen


@Composable
fun GeneraNav() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "SplashScreen", modifier = Modifier.fillMaxSize()){
        composable("SplashScreen"){
            SplashScreen(navController)
        }
        composable("OnBoardingScreen"){
            OnBoardingScreen(navController)
        }
        composable("auth"){
            Auth()
        }
    }
}