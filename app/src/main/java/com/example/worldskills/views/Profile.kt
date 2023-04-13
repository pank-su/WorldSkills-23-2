package com.example.worldskills.views

import android.app.Activity
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.worldskills.viewmodel.ProfileViewModel
import com.example.worldskills.viewmodel.genders
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile() {
    val context = LocalContext.current

    val profileViewModel: ProfileViewModel = viewModel()
    LaunchedEffect(null) {
        profileViewModel.init(context)
    }
    val scrollState = rememberScrollState()
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(
                text = "Карта пациента",
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center
            )
        }
        val re =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(),
                onResult = { uri ->
                    // Callback is invoked after the user selects a media item or closes the
                    // photo picker.
                    if (uri != null) {
                        profileViewModel.imageUri = uri


                    } else {
                        Log.d("PhotoPicker", "No media selected")
                    }
                })

        Box(modifier = Modifier
            .size(148.dp, 123.dp)
            .background(Color.Gray, CircleShape)
            .clip(CircleShape)
            .clickable {
                re.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }) {
            if (profileViewModel.imageUri != Uri.EMPTY) {
                Image(
                    painter = rememberAsyncImagePainter(profileViewModel.imageUri),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
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

        OutlinedTextField(value = profileViewModel.firstName,
            onValueChange = { profileViewModel.firstName = it },
            placeholder = {
                Text(
                    text = "Имя"
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color(
                    0xFFF5F5F9
                ), unfocusedBorderColor = Color.Transparent
            )
        )
        OutlinedTextField(
            value = profileViewModel.secondName,
            onValueChange = { profileViewModel.secondName = it },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color(
                    0xFFF5F5F9
                ), unfocusedBorderColor = Color.Transparent
            ),
            placeholder = {
                Text(
                    text = "Отчество"
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = profileViewModel.lastName,
            onValueChange = { profileViewModel.lastName = it },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color(
                    0xFFF5F5F9
                ), unfocusedBorderColor = Color.Transparent
            ),
            placeholder = {
                Text(
                    text = "Фамилия"
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(value = if (profileViewModel.dateBirth == null) "" else profileViewModel.dateBirth!!.format(
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
                    ), unfocusedBorderColor = Color.Transparent
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
                    DropdownMenuItem(text = { Text(text = el) }, onClick = {
                        profileViewModel.gender = el
                        expanded = false
                    })
                }

            }
        }


        val datePickerState = rememberDatePickerState()

        if (datePickerVis) DatePickerDialog(onDismissRequest = {
            datePickerVis = false
            profileViewModel.dateBirth =
                if (datePickerState.selectedDateMillis != null) Instant.ofEpochMilli(
                    datePickerState.selectedDateMillis!!
                ).atZone(ZoneId.systemDefault()).toLocalDate()
                else null
        }, confirmButton = {
            Button(
                onClick = {
                    datePickerVis = false
                    profileViewModel.dateBirth =
                        Instant.ofEpochMilli(datePickerState.selectedDateMillis!!)
                            .atZone(ZoneId.systemDefault()).toLocalDate()

                }, enabled = datePickerState.selectedDateMillis != null
            ) {
                Text(text = "Сохранить")
            }
        }) {

            DatePicker(state = datePickerState)

        }
        Button(
            onClick = { profileViewModel.create(context as Activity) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 28.dp)
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            enabled = profileViewModel.correct()
        ) {
            Text(text = "Создать")
        }
    }
}