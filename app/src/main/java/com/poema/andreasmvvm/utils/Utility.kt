package com.poema.andreasmvvm.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import android.R


object Utility {
    private var progressBar: ProgressBar? = null

    fun Context.isInternetAvailable(): Boolean {
        try {
            val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            return if (netInfo != null && netInfo.isConnected) {
                println("!!!VArit här i utils 'truevillkoret'")
                true
            } else {
                showErrorToast("Internet not available. Please try again!!")
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
                this, message,
                Toast.LENGTH_LONG).show()

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

    // visa progressbar
    fun Context.showProgressBar() {
        try {
            val layout = (this as? Activity)?.findViewById<View>(android.R.id.content)?.rootView as? ViewGroup

            progressBar = ProgressBar(this, null, R.attr.progressBarStyleLarge)
            progressBar?.let { it1 ->
                it1.isIndeterminate = true

                val params = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT
                )

                val rl = RelativeLayout(this)

                rl.gravity = Gravity.CENTER
                rl.addView(it1)

                layout?.addView(rl, params)

                it1.visibility = View.VISIBLE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // hide progressbar
    fun hideProgressBar() {
        try {
            progressBar?.let {
                it.visibility = View.GONE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}