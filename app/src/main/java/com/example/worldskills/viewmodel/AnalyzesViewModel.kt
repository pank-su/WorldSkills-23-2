package com.example.worldskills.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.worldskills.CONSTANTS
import com.example.worldskills.model.Add
import com.example.worldskills.model.Analyze
import com.example.worldskills.retrofit.ApiMedic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AnalyzesViewModel() : ViewModel() {
    val filteredAnalyzes: List<Analyze>
        get() {
            if (selectedCategory == "") return analyzes
            return analyzes.filter { a -> a.category == selectedCategory }
        }
    val apiMedic: ApiMedic = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
        .baseUrl(CONSTANTS.BASE_URL).build().create(ApiMedic::class.java)
    var adds: List<Add> by mutableStateOf(listOf<Add>())
    var analyzes: List<Analyze> by mutableStateOf(listOf())
    var catigories: Set<String> by mutableStateOf(setOf())
    var selectedCategory: String by mutableStateOf("")

    init {
        CoroutineScope(Dispatchers.IO).launch {
            adds = apiMedic.news()
        }
        CoroutineScope(Dispatchers.IO).launch {
            analyzes = apiMedic.catalog()
            catigories = analyzes.map { a -> a.category }.toSet()
        }
    }


}