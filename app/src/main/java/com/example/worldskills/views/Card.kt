package com.example.worldskills.views

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.worldskills.ui.theme.WorldSkillsTheme
import com.example.worldskills.viewmodel.ProfileViewModel
import com.example.worldskills.viewmodel.genders
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Card() {
    val profileViewModel: ProfileViewModel = viewModel()
    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp), verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Создание карты пациента",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.weight(3f)
            )
            TextButton(onClick = { /*TODO*/ }, Modifier.weight(2f)) {
                Text(text = "Пропустить")
            }
        }
        Column {
            Text(
                text = "Без карты пациента вы не сможете заказать анализы.",
                style = MaterialTheme.typography.labelSmall
            )
            Text(
                text = "В картах пациентов будут храниться результаты анализов вас и ваших близких.",
                style = MaterialTheme.typography.labelSmall
            )
        }

        var datePickerVis by remember { mutableStateOf(false) }

        OutlinedTextField(
            value = profileViewModel.firstName,
            onValueChange = { profileViewModel.firstName = it },
            placeholder = {
                Text(
                    text = "Имя"
                )
            }, modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color(
                    0xFFF5F5F9
                ),
                unfocusedBorderColor = Color.Transparent
            )
        )
        OutlinedTextField(
            value = profileViewModel.secondName,
            onValueChange = { profileViewModel.secondName = it },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color(
                    0xFFF5F5F9
                ),
                unfocusedBorderColor = Color.Transparent
            ),
            placeholder = {
                Text(
                    text = "Отчество"
                )
            }, modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = profileViewModel.lastName,
            onValueChange = { profileViewModel.lastName = it },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color(
                    0xFFF5F5F9
                ),
                unfocusedBorderColor = Color.Transparent
            ),
            placeholder = {
                Text(
                    text = "Фамилия"
                )
            }, modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = if (profileViewModel.dateBirth == null) "" else profileViewModel.dateBirth!!.format(
                DateTimeFormatter.ISO_LOCAL_DATE
            ),
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    datePickerVis = true

                },
            placeholder = { Text(text = "Дата рождения") },
            readOnly = true,
            enabled = false,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color(
                    0xFFF5F5F9
                ),
                unfocusedBorderColor = Color.Transparent,
                disabledTextColor = Color.Black,
                disabledPlaceholderColor = Color.Black,
                disabledBorderColor = Color.Transparent
            )
        )
        var expanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = profileViewModel.gender, onValueChange = {},
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color(
                        0xFFF5F5F9
                    ),
                    unfocusedBorderColor = Color.Transparent
                ),
                placeholder = { Text(text = "Пол") }, readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                for (el in genders) {
                    DropdownMenuItem(
                        text = { Text(text = el) },
                        onClick = {
                            profileViewModel.gender = el
                            expanded = false
                        })
                }

            }
        }


        val datePickerState = rememberDatePickerState()

        if (datePickerVis)
            DatePickerDialog(
                onDismissRequest = {
                    datePickerVis = false
                    profileViewModel.dateBirth =
                        if (datePickerState.selectedDateMillis != null)
                            Instant.ofEpochMilli(datePickerState.selectedDateMillis!!)
                                .atZone(ZoneId.systemDefault()).toLocalDate()
                        else null
                },
                confirmButton = {
                    Button(
                        onClick = {
                            datePickerVis = false
                            profileViewModel.dateBirth =
                                Instant.ofEpochMilli(datePickerState.selectedDateMillis!!)
                                    .atZone(ZoneId.systemDefault()).toLocalDate()

                        },
                        enabled = datePickerState.selectedDateMillis != null
                    ) {
                        Text(text = "Сохранить")
                    }
                }) {

                DatePicker(state = datePickerState)

            }
        val context = LocalContext.current
        Button(
            onClick = { profileViewModel.create(context as Activity) },
            modifier = Modifier
                .fillMaxWidth().padding(0.dp, 28.dp)
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            enabled = profileViewModel.correct()
        ) {
            Text(text = "Создать")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewCard() {
    WorldSkillsTheme {
        Card()
    }

}