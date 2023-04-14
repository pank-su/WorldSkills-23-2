package com.example.worldskills.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.worldskills.R
import com.example.worldskills.model.Analyze
import com.example.worldskills.ui.theme.WorldSkillsTheme
import com.example.worldskills.viewmodel.AnalyzesViewModel

@Composable
fun Cart(analyzesViewModel: AnalyzesViewModel = viewModel(), navController: NavController) {

    Box(Modifier.fillMaxSize()) {

        FilledIconButton(
            onClick = {navController.popBackStack()},
            modifier = Modifier.padding(20.dp),
            shape = RoundedCornerShape(8.dp),
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = Color(0xFFF5F5F9), contentColor = Color(0xFF7E7E9A)
            )
        ) {
            Icon(Icons.Default.KeyboardArrowLeft, null)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 100.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Корзина", style = MaterialTheme.typography.titleSmall)
                IconButton(
                    onClick = { analyzesViewModel.cart.clear() },
                    colors = IconButtonDefaults.iconButtonColors(contentColor = Color(0xFFB8C1CC))
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.garbage), contentDescription = ""
                    )
                }
            }
            LazyColumn(modifier = Modifier.height(280.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(analyzesViewModel.cart.toSet().toList()) {
                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(38.dp)
                        ) {
                            Text(
                                text = it.name,
                                style = MaterialTheme.typography.titleSmall,
                            )
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {

                                Text(
                                    text = it.price + " ₽",
                                    style = MaterialTheme.typography.titleSmall
                                )

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        "${analyzesViewModel.cart.count { a -> it == a }} пациент",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontSize = 20.sp
                                    )
                                    FilledIconButton(onClick = { analyzesViewModel.cart.remove(it) }) {
                                        Icon(
                                            painterResource(id = R.drawable.minus),
                                            contentDescription = null
                                        )
                                    }
                                    FilledIconButton(onClick = { analyzesViewModel.cart.add(it) }) {
                                        Icon(
                                            painterResource(id = R.drawable.plus),
                                            contentDescription = null
                                        )
                                    }
                                }
                            }
                        }


                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Сумма", style = MaterialTheme.typography.titleSmall)
                Text(
                    text = "${analyzesViewModel.cart.sumOf { c -> c.price.toInt() }} ₽",
                    style = MaterialTheme.typography.titleSmall
                )
            }

        }
        Button(
            onClick = { navController.navigate("order") },
            modifier = Modifier
                .fillMaxWidth()
                .height(112.dp)
                .align(Alignment.BottomCenter)
                .padding(20.dp, 32.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1A6FEE), contentColor = Color.White
            )
        ) {
            Text(text = "Перейти к оформлению заказа")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun previewCart() {
    WorldSkillsTheme {
        val analyzesViewModel: AnalyzesViewModel = viewModel()
        analyzesViewModel.cart.add(
            Analyze(
                "Test", "Test", "Test",
                "Test", "Test", "1000", "Test"
            )
        )
        Cart(analyzesViewModel, rememberNavController())
    }
}