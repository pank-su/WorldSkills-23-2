package com.example.worldskills.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.worldskills.R
import com.example.worldskills.viewmodel.AuthViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailEnter(navController: NavHostController, viewModel: AuthViewModel) {
    Box(modifier = Modifier
        .padding(25.dp, 56.dp)
        .fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painterResource(id = R.drawable.hello),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                )
                Text("Добро пожаловать", style = MaterialTheme.typography.titleSmall)
            }
            Text(text = "Войдите в приложение, чтобы пользоваться функциями приложения")
            Column {
                Text(text = "Вход по E-mail", style = MaterialTheme.typography.labelSmall)
                OutlinedTextField(
                    value = viewModel.email,
                    onValueChange = { t -> viewModel.email = t },
                    // label = { /*Text(text = "Вход по E-mail")*/ },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color(
                            0xFFF5F5F9
                        ),
                        unfocusedBorderColor = Color.Transparent
                    ),
                    placeholder = { Text(text = "Введите email")},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
            }
            val isEnabled by remember {
                derivedStateOf { viewModel.checkEmail() }
            }
            Button(
                onClick = { viewModel.sendCode(navController) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                enabled = isEnabled
            ) {
                Text(text = "Далее")
            }

        }
        Column(modifier = Modifier.align(Alignment.BottomStart)) {
            Text("Или войдите с помощью ", style = MaterialTheme.typography.labelSmall, modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 16.dp), textAlign = TextAlign.Center)
            OutlinedButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Войти с Яндекс", color = Color.Black)
            }
        }
    }
}
