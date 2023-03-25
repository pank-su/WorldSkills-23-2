package com.example.worldskills.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.worldskills.R
import com.example.worldskills.ui.theme.WorldSkillsTheme
import com.example.worldskills.viewmodel.AuthViewModel

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun PinCode(viewModel: AuthViewModel = androidx.lifecycle.viewmodel.compose.viewModel(), navController: NavHostController) {
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.verticalScroll(scrollState)) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 40.dp, 10.dp, 0.dp)) {
            TextButton(
                onClick = { /*TODO*/ }, modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(
                        30.dp, 0.dp, 0.dp,
                        0.dp
                    )
            ) {
                Text(text = "Пропустить")
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(44.dp)
                ,
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {

            Text(
                text = "Создайте пароль",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
            )
            Text(
                text = "Для защиты ваших персональных данных",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(0.dp, 16.dp, 0.dp, 0.dp)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    12.dp,
                    alignment = Alignment.CenterHorizontally
                ), modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 60.dp)
            ) {
                for (i in 0 ..< viewModel.pinCodeLen) {
                    if (i < viewModel.pinCode.length)
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primaryContainer)
                        )
                    else
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .border(
                                    1.dp,
                                    MaterialTheme.colorScheme.primaryContainer,
                                    shape = CircleShape
                                )
                        )
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                for (i in 0..2) {
                    Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                        for (j in 1..3) {
                            Button(
                                modifier = Modifier.size(80.dp),
                                onClick = { if (viewModel.pinCode.length != viewModel.pinCodeLen) viewModel.pinCode += (i * 3 + j).toString() },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFF5F5F9),
                                    contentColor = Color.Black
                                )
                            ) {
                                Text(text = "${i * 3 + j}", textAlign = TextAlign.Center)
                            }
                        }
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                    Box(Modifier.size(80.dp)) {

                    }
                    Button(
                        modifier = Modifier.size(80.dp),
                        onClick = { if (viewModel.pinCode.length != viewModel.pinCodeLen) viewModel.pinCode += (0).toString() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFF5F5F9),
                            contentColor = Color.Black
                        )
                    ) {
                        Text(text = "0", textAlign = TextAlign.Center)
                    }
                    IconButton(
                        onClick = {
                            if (viewModel.pinCode.length > 0)
                                viewModel.pinCode =
                                    viewModel.pinCode.substring(0..viewModel.pinCode.length - 2)
                        },
                        modifier = Modifier.size(80.dp),
                        colors = IconButtonDefaults.iconButtonColors(contentColor = Color.Black)
                    ) {
                        Icon(painterResource(id = R.drawable.del_icon), null)
                    }
                }
                val codeLen by remember{
                    derivedStateOf { viewModel.pinCode.length == viewModel.pinCodeLen }
                }

                val context = LocalContext.current
                if (codeLen) {
                    LaunchedEffect(codeLen){
                        viewModel.saveCode(context, navController)
                    }

                }


            }
        }


    }
}