package com.poema.andreasmvvm.utils

import android.content.Context
import android.net.ConnectivityManager
import android.widget.ProgressBar
import android.widget.Toast
import com.poema.andreasmvvm.activities.BaseActivity


object Utility {
    private var progressBar: ProgressBar? = null

    fun Context.isInternetAvailable(): Boolean {

        try {
            val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            return if (netInfo != null && netInfo.isConnected) {
                println("!!!Varit här i utils 'truevillkoret'")
                true
            } else {
                showErrorToast("Internet not available. Please check your connection!")
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    fun Context.showErrorToast(message: String?) {

        try {
            Toast.makeText(
                applicationContext, message,
                Toast.LENGTH_LONG
            ).show()

            // set message color
           /* val textView = toast.view?.findViewById(android.R.id.message) as? TextView
            textView?.setTextColor(Color.WHITE)
            textView?.gravity = Gravity.CENTER

            // set background color
            toast.view?.setBackgroundColor(Color.RED)

            toast.setGravity(Gravity.TOP or Gravity.FILL_HORIZONTAL, 0, 0)*/


        } catch (e: Exception) {
           e.printStackTrace()
        }

    }
}