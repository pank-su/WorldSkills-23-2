package com.example.worldskills.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.worldskills.ui.theme.WorldSkillsTheme
import com.example.worldskills.viewmodel.OrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Order(navController: NavHostController, orderViewModel: OrderViewModel = viewModel()) {
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 100.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
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
                        .fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = containerColor,
                        unfocusedContainerColor = containerColor,
                        disabledContainerColor = containerColor,
                        unfocusedBorderColor = Color.Transparent,
                    ),
                    placeholder = { Text(text = "Введите ваш адрес")},
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
                    placeholder = { Text(text = "Выберите дату и время")},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
            }
            Column {
                Text(text = "Кто будет сдавать анализы? *", style = MaterialTheme.typography.labelSmall)
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
                    trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = false)},
                    placeholder = { Text(text = "Пациента")},
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
                    placeholder = { Text(text = "Введите ваш телефон")},
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
                        .fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = containerColor,
                        unfocusedContainerColor = containerColor,
                        disabledContainerColor = containerColor,
                        unfocusedBorderColor = Color.Transparent,
                    ),
                    placeholder = { Text(text = "Можете оставить свои пожелания")},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )
            }
        }
    }
}



@Preview
@Composable
fun previewOrder() {
    WorldSkillsTheme() {
        Order(navController = rememberNavController())
    }
}