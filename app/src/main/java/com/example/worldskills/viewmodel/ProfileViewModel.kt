package com.example.worldskills.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.worldskills.model.Profile
import com.example.worldskills.retrofit.ApiMedic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.time.LocalDate
import java.util.Date

val genders: Set<String> = setOf("Мужской", "Женский")

class ProfileViewModel: ViewModel() {

    val retrofit = Retrofit.Builder().baseUrl("https://medic.madskill.ru/api/")
        .addConverterFactory(GsonConverterFactory.create()).build()
    fun correct(): Boolean = lastName.isNotBlank() && firstName.isNotBlank() && secondName.isNotBlank() && dateBirth != null && gender.isNotBlank()
    fun create() {
        CoroutineScope(Dispatchers.IO).launch {
            val profile = retrofit.create(ApiMedic::class.java).createProfile(Profile(firstName, lastName, secondName, dateBirth!!, gender))
            println(profile)
        }
    }

    var lastName by mutableStateOf("")
    var firstName  by mutableStateOf("")
    var secondName by mutableStateOf("")
    var dateBirth: LocalDate? by mutableStateOf(null)
    var gender: String by mutableStateOf("")
}