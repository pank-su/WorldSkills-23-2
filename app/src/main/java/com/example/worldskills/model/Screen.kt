package com.example.worldskills.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable

data class Screen(
    val route: String,
    @DrawableRes val drawableRes: Int,
    val composable: @Composable () -> Unit
)
