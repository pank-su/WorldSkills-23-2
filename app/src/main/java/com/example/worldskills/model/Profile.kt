package com.example.worldskills.model

import java.time.LocalDate

data class Profile(var firstname: String, var firstName: String, var middlename: String, var bith: LocalDate, var pol: String, var image: String = "", val id: Int = 0)
