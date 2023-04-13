package com.example.worldskills.viewmodel

import android.app.Activity
import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.worldskills.retrofit.ApiMedic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.nio.charset.Charset
import java.security.MessageDigest

class AuthViewModel : ViewModel() {

    val retrofit = Retrofit.Builder().baseUrl("https://medic.madskill.ru/api/")
        .addConverterFactory(GsonConverterFactory.create()).build()
    var email by mutableStateOf("")
    lateinit var token: String

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
            val th = this
            CoroutineScope(Dispatchers.Default).launch {
                val message = retrofit.create(ApiMedic::class.java).sendCode(email)

                if (message.message == "Успешно код отправлен") {
                    th.start()
                }
            }

        }

    }

    var pinCode by mutableStateOf("")
    val pinCodeLen = 4

    fun sendCode(navController: NavHostController) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val message = retrofit.create(ApiMedic::class.java).sendCode(email)

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

    fun codeCorrect(navController: NavHostController, activity: Activity) {
        CoroutineScope(Dispatchers.IO).launch {
            try {

                token = retrofit.create(ApiMedic::class.java).signIn(email, code.toInt()).token
                timer.cancel()
                val sharedPreferences =
                    activity.getSharedPreferences("settings", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putBoolean("is_auth", true)
                editor.putString("token", token)
                editor.apply()
                CoroutineScope(Dispatchers.Main).launch {
                    navController.navigate("pin") {
                        popUpTo("otp") {
                            inclusive = true
                        }
                    }
                }
            } catch (e: HttpException) {
                TODO()
            }
        }
    }

    fun saveCode(context: Context, navController: NavHostController) {
        File(context.filesDir, "secret").writeBytes(
            MessageDigest.getInstance("SHA-256").digest(pinCode.toByteArray())
        )
        val sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("has_pin", true)
        editor.apply()
        CoroutineScope(Dispatchers.Main).launch {
            navController.navigate("card") {
                popUpTo("pin") {
                    inclusive = true
                }
            }
        }
    }

    fun checkCode(context: Context, navController: NavHostController) {
        val pass = File(context.filesDir, "secret").readBytes().toString(Charset.defaultCharset())
        println(pass)
        if (pass == MessageDigest.getInstance("SHA-256").digest(pinCode.toByteArray()).toString(
                Charset.defaultCharset()
            )
        ) {
            navController.navigate("main") {
                popUpTo("pin") {
                    inclusive = true
                }
            }
        } else {
            pinCode = ""
        }
    }


}