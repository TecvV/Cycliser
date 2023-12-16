package com.example.app

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.util.*
import kotlin.concurrent.schedule

class disconnected : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disconnected)
        window.statusBarColor = ContextCompat.getColor(this@disconnected,R.color.backgroundMA)
        window.navigationBarColor = ContextCompat.getColor(this@disconnected,R.color.backgroundMA)

        delayrun()

    }
    private fun delaymethod() {
        if (checkForInternet(this)) {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this, disconnected::class.java)
            startActivity(intent)
        }
    }

    private fun delayrun(){
        Timer().schedule(5000){
            delaymethod()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finishAndRemoveTask()
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun checkForInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
}