package com.example.worldskills.viewmodel

import android.os.CountDownTimer
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.worldskills.retrofit.ApiFood
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthViewModel : ViewModel() {

    val retrofit = Retrofit.Builder().baseUrl("https://medic.madskill.ru/api/")
        .addConverterFactory(GsonConverterFactory.create()).build()
    var email by mutableStateOf("")
    var saveEmail = ""

    fun checkEmail(): Boolean =
        Regex("[a-z0-9]{1,256}@[a-z0-9]{1,256}\\.[a-z]{1,256}").matches(email)

    var code by mutableStateOf("")
    val codeLength = 4

    var secs by mutableStateOf(60)
    var timerIsFinish by mutableStateOf(false)

    val timer = object : CountDownTimer(60000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            secs = (millisUntilFinished / 1000).toInt()
        }

        override fun onFinish() {
            timerIsFinish = true
        }

    }

    fun sendCode(navController: NavHostController) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                saveEmail = email
                val message = retrofit.create(ApiFood::class.java).sendCode(email)

                if (message.message == "Успешно код отправлен") {
                    CoroutineScope(Dispatchers.Main).launch {
                        navController.navigate("otp")
                    }

                }
            } catch (e: HttpException) {
                // Log.e("qwerty", message.errors)
                Log.e("qwerty", e.response()!!.code().toString())
            }
        }

    }

    fun codeCorrect(navController: NavHostController) {
        CoroutineScope(Dispatchers.IO).launch {
            try {

                val token = retrofit.create(ApiFood::class.java).signIn(saveEmail, code.toInt())
                CoroutineScope(Dispatchers.Main).launch {
                    navController.navigate("pin")
                }
            } catch (e: HttpException) {
                // Log.e("qwerty", message.errors)
                Log.e("qwerty", saveEmail)
                Log.e("qwerty", code)
                Log.e("qwerty", e.response()!!.code().toString())
                Log.e("qwerty", e.response()!!.body().toString())
            }
        }
    }


}