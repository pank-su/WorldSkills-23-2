package com.example.worldskills.views

import android.app.Activity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.worldskills.viewmodel.AuthViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun OTPEnter(navController: NavHostController, viewModel: AuthViewModel) {

    Box(modifier = Modifier.fillMaxSize()){
        FilledIconButton(onClick = {
            viewModel.code = ""
            viewModel.timer.cancel()
            navController.popBackStack() }, modifier = Modifier.padding(20.dp), shape = RoundedCornerShape(8.dp), colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color(0xFFF5F5F9), contentColor = Color(0xFF7E7E9A))) {
            Icon(Icons.Default.KeyboardArrowLeft, null)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 0.dp, 0.dp, 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) { Text("Введите код из E-mail", modifier = Modifier.padding(0.dp, 24.dp), style = MaterialTheme.typography.labelLarge)
        val context = LocalContext.current
        BasicTextField(value = viewModel.code,
            onValueChange = { t ->
                if (t.length <= viewModel.codeLength)
                    viewModel.code = t
                if (t.length == viewModel.codeLength)
                    viewModel.codeCorrect(navController, context as Activity)

            },
            decorationBox = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        16.dp,
                        alignment = Alignment.CenterHorizontally
                    ), modifier = Modifier.fillMaxWidth()
                ) {
                    repeat(viewModel.codeLength) {
                        cellView(char = viewModel.code.getOrNull(it) ?: ' ')
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )


        LaunchedEffect(null) {
            viewModel.timer.start()
        }

        Text(
            text = "Отправить код повторно можно будет через ${viewModel.secs} секунд",
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(0.dp, 16.dp)
                .width(250.dp),
            lineHeight = 20.sp
        )
    }

}

@Composable
fun cellView(char: Char) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .background(Color(0xFFF5F5F9), shape = RoundedCornerShape(10.dp)).border(1.dp, Color(0xFFEBEBEB),  shape = RoundedCornerShape(10.dp))
    ) {
        Text(
            text = char.toString(),
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.W400
        )
    }

}


