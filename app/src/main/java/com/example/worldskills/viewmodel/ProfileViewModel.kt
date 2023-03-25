package com.example.worldskills.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.worldskills.model.Profile
import java.time.LocalDate
import java.util.Date

val genders: Set<String> = setOf("Мужской", "Женский")

class ProfileViewModel: ViewModel() {
    fun correct(): Boolean = lastName.isNotBlank() && firstName.isNotBlank() && secondName.isNotBlank() && dateBirth != null && gender.isNotBlank()
    fun create() {
        TODO("Not yet implemented")
    }

    var lastName by mutableStateOf("")
    var firstName  by mutableStateOf("")
    var secondName by mutableStateOf("")
    var dateBirth: LocalDate? by mutableStateOf(null)
    var gender: String by mutableStateOf("")
}