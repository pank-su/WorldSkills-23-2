package com.example.worldskills.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.worldskills.model.Analyze
import com.example.worldskills.model.Profile

class OrderViewModel : ViewModel() {
    fun ordered(cart: List<Analyze>) {

    }

    init {

    }

    val canAddress: Boolean
        get() {
            return address.isNotBlank()
        }
    var isGeo: Boolean by mutableStateOf(false)
    var isSaved: Boolean by mutableStateOf(false)
    var intercom: String by mutableStateOf("")
    var floor: String by mutableStateOf("")
    var entrance: String by mutableStateOf("")
    var apart: String by mutableStateOf("")
    var height: String by mutableStateOf("")
    val canOrder: Boolean
        get() {
            return profile != null && dateText.isNotBlank() && geoText.isNotBlank() && phoneNumber.isNotBlank()
        }
    lateinit var cart: List<Analyze>
    var profile: Profile? by mutableStateOf(null)
    val phoneNumber by mutableStateOf("")
    var dateText: String by mutableStateOf("")
    var geoText: String by mutableStateOf("")

    var address: String by mutableStateOf("")
    var location: Pair<Double, Double> by mutableStateOf(Pair(0.0, 0.0))

}
