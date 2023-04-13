package com.example.worldskills.model

data class Profile(
    var firstname: String,
    var lastname: String,
    var middlename: String,
    var bith: String,
    var pol: String,
    var image: String = "",
    val id: Int = 0
)
