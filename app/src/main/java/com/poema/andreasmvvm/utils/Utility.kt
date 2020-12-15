package com.poema.andreasmvvm.utils

import android.content.Context
import android.net.ConnectivityManager
import android.widget.ProgressBar
import android.widget.Toast
import com.poema.andreasmvvm.activities.BaseActivity


object Utility {

    fun Context.isInternetAvailable(): Boolean {

            val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            return if (netInfo != null && netInfo.isConnected) {
                println("!!!Utils: är uppkopplad")
                true
            } else {
                showErrorToast("Internet not available. Please check your connection!")
                false
            }
    }

    fun Context.showErrorToast(message: String?) {

            Toast.makeText(
                applicationContext, message,
                Toast.LENGTH_SHORT
            ).show()

    }
}