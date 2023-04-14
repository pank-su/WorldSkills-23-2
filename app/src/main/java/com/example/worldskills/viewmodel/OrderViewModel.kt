package com.example.worldskills.viewmodel

import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.worldskills.model.Profile

class OrderViewModel: ViewModel() {

    var profile: Profile? by mutableStateOf(null)
    var dateText: String by mutableStateOf("")
    var geoText: String by mutableStateOf("")

    var address: String by mutableStateOf("")
    var location: Pair<Double, Double> by mutableStateOf(Pair(0.0, 0.0))


}
