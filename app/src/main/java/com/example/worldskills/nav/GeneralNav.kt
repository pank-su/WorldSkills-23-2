package com.example.worldskills.nav

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.worldskills.viewmodel.AnalyzesViewModel
import com.example.worldskills.viewmodel.AuthViewModel
import com.example.worldskills.views.Card
import com.example.worldskills.views.Cart
import com.example.worldskills.views.EmailEnter
import com.example.worldskills.views.MainScreen
import com.example.worldskills.views.OTPEnter
import com.example.worldskills.views.OnBoardingScreen
import com.example.worldskills.views.Order
import com.example.worldskills.views.PinCode
import com.example.worldskills.views.SplashScreen


@Composable
fun GeneraNav() {
    val navController = rememberNavController()
    val viewModel = viewModel(AuthViewModel::class.java)
    val analyzesViewModel: AnalyzesViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    NavHost(navController = navController, startDestination = "SplashScreen", modifier = Modifier.fillMaxSize()){
        composable("SplashScreen"){
            SplashScreen(navController)
        }
        composable("OnBoardingScreen"){
            OnBoardingScreen(navController)
        }
        composable("auth"){
            EmailEnter(navController, viewModel)
        }
        composable("otp"){
            OTPEnter(navController, viewModel)
        }
        composable("pin"){
            PinCode(viewModel, navController)
        }
        composable("card"){
            Card()
        }
        composable("main"){
            MainScreen(analyzesViewModel, navController)
        }
        composable("cart"){
            Cart(analyzesViewModel = analyzesViewModel, navController)
        }
        composable("order"){
            Order(navController = navController, analyzesViewModel.cart)
        }
    }
}