package com.poema.andreasmvvm.utils

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast


object Utility {

    fun Context.isInternetAvailable(): Boolean {

            val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            return if (netInfo != null && netInfo.isConnected) {
                true
            } else {
                showErrorToast("Internet not available. Restricted to cached drinks. Please check your connection!")
                false
            }
    }

    fun Context.showErrorToast(message: String?) {

            Toast.makeText(
                applicationContext, message,
                Toast.LENGTH_LONG
            ).show()

    }
}