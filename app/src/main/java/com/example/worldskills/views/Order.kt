package com.example.worldskills.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.worldskills.R
import com.example.worldskills.model.Analyze
import com.example.worldskills.ui.theme.WorldSkillsTheme
import com.example.worldskills.viewmodel.OrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Order(
    navController: NavHostController,
    cart: List<Analyze> = listOf(),
    orderViewModel: OrderViewModel = viewModel()
) {
    Box(Modifier.fillMaxSize()) {
        FilledIconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.padding(20.dp),
            shape = RoundedCornerShape(8.dp),
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = Color(0xFFF5F5F9), contentColor = Color(0xFF7E7E9A)
            )
        ) {
            Icon(Icons.Default.KeyboardArrowLeft, null)
        }
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 100.dp, 20.dp, 0.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(32.dp),

            ) {
            Column {
                Text(text = "Адрес *", style = MaterialTheme.typography.labelSmall)
                val containerColor = Color(
                    0xFFF5F5F9
                )
                OutlinedTextField(
                    value = orderViewModel.geoText,
                    onValueChange = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = containerColor,
                        unfocusedContainerColor = containerColor,
                        disabledContainerColor = containerColor,
                        unfocusedBorderColor = Color.Transparent,
                    ),
                    placeholder = { Text(text = "Введите ваш адрес") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
            }
            Column {
                Text(text = "Дата и время*", style = MaterialTheme.typography.labelSmall)
                val containerColor = Color(
                    0xFFF5F5F9
                )
                OutlinedTextField(
                    value = orderViewModel.dateText,
                    onValueChange = { },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = containerColor,
                        unfocusedContainerColor = containerColor,
                        disabledContainerColor = containerColor,
                        unfocusedBorderColor = Color.Transparent,
                    ),
                    placeholder = { Text(text = "Выберите дату и время") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
            }
            Column {
                Text(
                    text = "Кто будет сдавать анализы? *",
                    style = MaterialTheme.typography.labelSmall
                )
                val containerColor = Color(
                    0xFFF5F5F9
                )
                OutlinedTextField(
                    value = orderViewModel.profile?.firstname ?: "",
                    onValueChange = { },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = containerColor,
                        unfocusedContainerColor = containerColor,
                        disabledContainerColor = containerColor,
                        unfocusedBorderColor = Color.Transparent,
                    ),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
                    placeholder = { Text(text = "Пациент") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
            }
            Column {
                Text(text = "Телефон *", style = MaterialTheme.typography.labelSmall)
                val containerColor = Color(
                    0xFFF5F5F9
                )
                OutlinedTextField(
                    value = orderViewModel.profile?.firstname ?: "",
                    onValueChange = { },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = containerColor,
                        unfocusedContainerColor = containerColor,
                        disabledContainerColor = containerColor,
                        unfocusedBorderColor = Color.Transparent,
                    ),
                    placeholder = { Text(text = "Введите ваш телефон") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )
            }
            Column {
                Text(text = "Комментарий", style = MaterialTheme.typography.labelSmall)
                val containerColor = Color(
                    0xFFF5F5F9
                )
                OutlinedTextField(
                    value = orderViewModel.profile?.firstname ?: "",
                    onValueChange = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(138.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = containerColor,
                        unfocusedContainerColor = containerColor,
                        disabledContainerColor = containerColor,
                        unfocusedBorderColor = Color.Transparent,
                    ),
                    placeholder = { Text(text = "Можете оставить свои пожелания") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Оплата Apple Pay")
                Icon(Icons.Default.KeyboardArrowRight, null)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Промокод", style = MaterialTheme.typography.labelSmall)
                Icon(Icons.Default.KeyboardArrowRight, null)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "${cart.size} анализов", style = MaterialTheme.typography.labelSmall)
                Text(text = "${cart.sumOf { a -> a.price.toInt() }} ₽")
            }
            Button(
                onClick = { orderViewModel.ordered(cart) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(78.dp)
                    .padding(0.dp, 0.dp, 0.dp, 32.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                enabled = orderViewModel.canOrder
            ) {
                Text(text = "Заказать")
            }
        }
    }
}


@Composable
fun Address(orderViewModel: OrderViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Адрес сдачи анализов", style = MaterialTheme.typography.titleSmall)
            Icon(
                painter = painterResource(id = R.drawable.map_icon),
                contentDescription = null,
                tint = Color.Gray
            )

        }
        Column {
            Text(text = "Ваш адрес", style = MaterialTheme.typography.labelSmall)
            val containerColor = Color(
                0xFFF5F5F9
            )
            OutlinedTextField(
                value = orderViewModel.address,
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = containerColor,
                    unfocusedContainerColor = containerColor,
                    disabledContainerColor = containerColor,
                    unfocusedBorderColor = Color.Transparent,
                ),
                placeholder = { Text(text = "Ваш адрес") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column {
                    Text(text = "Долгота", style = MaterialTheme.typography.labelSmall)
                    val containerColor = Color(
                        0xFFF5F5F9
                    )
                    OutlinedTextField(
                        value = orderViewModel.location.first.toString() ?: "",
                        onValueChange = {
                            orderViewModel.location =
                                Pair(it.toDouble(), orderViewModel.location.second)
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = containerColor,
                            unfocusedContainerColor = containerColor,
                            disabledContainerColor = containerColor,
                            unfocusedBorderColor = Color.Transparent,
                        ),
                        placeholder = { Text(text = "") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            }
            item {
                Column {
                    Text(text = "Широта", style = MaterialTheme.typography.labelSmall)
                    val containerColor = Color(
                        0xFFF5F5F9
                    )
                    OutlinedTextField(
                        value = orderViewModel.location.second.toString() ?: "",
                        onValueChange = {
                            orderViewModel.location =
                                Pair(orderViewModel.location.first, it.toDouble())
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = containerColor,
                            unfocusedContainerColor = containerColor,
                            disabledContainerColor = containerColor,
                            unfocusedBorderColor = Color.Transparent,
                        ),
                        placeholder = { Text(text = "") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            }
            item {
                Column {
                    Text(text = "Высота", style = MaterialTheme.typography.labelSmall)
                    val containerColor = Color(
                        0xFFF5F5F9
                    )
                    OutlinedTextField(
                        value = orderViewModel.height,
                        onValueChange = { orderViewModel.height = it },
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = containerColor,
                            unfocusedContainerColor = containerColor,
                            disabledContainerColor = containerColor,
                            unfocusedBorderColor = Color.Transparent,
                        ),
                        placeholder = { Text(text = "") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            }
            item {
                Column {
                    Text(text = "Квартира", style = MaterialTheme.typography.labelSmall)
                    val containerColor = Color(
                        0xFFF5F5F9
                    )
                    OutlinedTextField(
                        value = orderViewModel.apart,
                        onValueChange = { orderViewModel.apart = it },
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = containerColor,
                            unfocusedContainerColor = containerColor,
                            disabledContainerColor = containerColor,
                            unfocusedBorderColor = Color.Transparent,
                        ),
                        placeholder = { Text(text = "") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            }
            item {
                Column {
                    Text(text = "Подъезд", style = MaterialTheme.typography.labelSmall)
                    val containerColor = Color(
                        0xFFF5F5F9
                    )
                    OutlinedTextField(
                        value = orderViewModel.entrance,
                        onValueChange = { orderViewModel.entrance = it },
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = containerColor,
                            unfocusedContainerColor = containerColor,
                            disabledContainerColor = containerColor,
                            unfocusedBorderColor = Color.Transparent,
                        ),
                        placeholder = { Text(text = "") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            }
            item {
                Column {
                    Text(text = "Этаж", style = MaterialTheme.typography.labelSmall)
                    val containerColor = Color(
                        0xFFF5F5F9
                    )
                    OutlinedTextField(
                        value = orderViewModel.floor,
                        onValueChange = { orderViewModel.floor = it },
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = containerColor,
                            unfocusedContainerColor = containerColor,
                            disabledContainerColor = containerColor,
                            unfocusedBorderColor = Color.Transparent,
                        ),
                        placeholder = { Text(text = "") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            }

        }
        Column {
            Text(text = "Домофон", style = MaterialTheme.typography.labelSmall)
            val containerColor = Color(
                0xFFF5F5F9
            )
            OutlinedTextField(
                value = orderViewModel.intercom,
                onValueChange = { orderViewModel.intercom = it },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = containerColor,
                    unfocusedContainerColor = containerColor,
                    disabledContainerColor = containerColor,
                    unfocusedBorderColor = Color.Transparent,
                ),
                placeholder = { Text(text = "") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Сохранить этот адрес?",
                style = MaterialTheme.typography.titleSmall,
                fontSize = 20.sp
            )
            Switch(
                checked = orderViewModel.isSaved,
                onCheckedChange = { orderViewModel.isSaved = it })
        }
        if (orderViewModel.isSaved) {
            val containerColor = Color(
                0xFFF5F5F9
            )
            OutlinedTextField(
                value = orderViewModel.intercom,
                onValueChange = { orderViewModel.intercom = it },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = containerColor,
                    unfocusedContainerColor = containerColor,
                    disabledContainerColor = containerColor,
                    unfocusedBorderColor = Color.Transparent,
                ),
                placeholder = { Text(text = "Название: например дом, работа") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
        }
        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            enabled = orderViewModel.canOrder
        ) {
            Text(text = "Подтвердить")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun previewOrder() {
    WorldSkillsTheme {
        // Order(navController = rememberNavController())
        Address()
    }
}