package com.example.worldskills.viewmodel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import com.example.worldskills.model.Profile
import com.example.worldskills.retrofit.ApiMedic
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.time.LocalDate
import java.util.Base64

val genders: Set<String> = setOf("Мужской", "Женский")

class ProfileViewModel() : ViewModel() {

    var lastName by mutableStateOf("")
    var firstName by mutableStateOf("")
    var secondName by mutableStateOf("")
    var dateBirth: LocalDate? by mutableStateOf(null)
    var gender: String by mutableStateOf("")

    var imageUri: Uri by mutableStateOf(Uri.EMPTY)
    val retrofit = Retrofit.Builder().baseUrl("https://medic.madskill.ru/api/")
        .addConverterFactory(GsonConverterFactory.create()).build()

    var profile: Profile? = null

    fun init(activity: Context) {
        val sharedPreferences = activity.getSharedPreferences("settings", Context.MODE_PRIVATE)
        try {
            profile =
                Gson().fromJson(sharedPreferences.getString("profile", "{}"), Profile::class.java)
            lastName = profile!!.lastname
            firstName = profile!!.firstname
            secondName = profile!!.middlename
            dateBirth = LocalDate.parse(profile!!.bith)
            gender = profile!!.pol
            println(profile!!.image)
            val file = File(activity.cacheDir, "image")

            file.outputStream().write(Base64.getDecoder().decode(profile!!.image))
            imageUri = file.toUri()
        } catch (e: ArrayIndexOutOfBoundsException) {
            println(e.message)
        }
    }

    fun correct(): Boolean =
        lastName.isNotBlank() && firstName.isNotBlank() && secondName.isNotBlank() && dateBirth != null && gender.isNotBlank()

    fun create(activity: Activity) {
        val sharedPreferences = activity.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val token = "Bearer " + sharedPreferences.getString("token", "")
        println(token)
        val editor = sharedPreferences.edit()
        if (token == "Bearer ") return
        CoroutineScope(Dispatchers.IO).launch {
            val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
            activity.contentResolver.takePersistableUriPermission(imageUri, flag)
            println(profile)
            if (profile?.firstname == null) {
                profile = retrofit.create(ApiMedic::class.java).createProfile(
                        Profile(firstName, lastName, secondName, dateBirth!!.toString(), gender),
                        token
                    )
                println("ok")
            } else {
                val inputStream = activity.contentResolver.openInputStream(imageUri)!!
                val bytes = inputStream.readBytes()
                withContext(Dispatchers.IO) {
                    inputStream.close()
                }
                retrofit.create(ApiMedic::class.java).updateProfile(
                        Profile(
                            firstName,
                            lastName,
                            secondName,
                            dateBirth!!.toString(),
                            gender,
                            image = Base64.getEncoder().encodeToString(bytes)
                        ), token
                    )
                profile = Profile(
                    firstName,
                    lastName,
                    secondName,
                    dateBirth!!.toString(),
                    gender,
                    image = Base64.getEncoder().encodeToString(bytes)
                )
            }
            println(profile)
            editor.putString(
                "profile", Gson().toJson(profile, Profile::class.java)
            )

            editor.apply()
            editor.commit()
        }
    }


}