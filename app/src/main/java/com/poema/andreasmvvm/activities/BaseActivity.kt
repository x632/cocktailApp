package com.poema.andreasmvvm.activities

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.poema.andreasmvvm.R

abstract class BaseActivity : AppCompatActivity() {

    lateinit var progressBar: ProgressBar

    override fun setContentView(layoutResID: Int) {
        val constraintLayout = layoutInflater.inflate(R.layout.activity_base, null) as ConstraintLayout
        val frameLayout = constraintLayout.findViewById<FrameLayout>(R.id.activity_content)
        progressBar = constraintLayout.findViewById(R.id.progress_bar)
        progressBar.indeterminateTintList = ColorStateList.valueOf(Color.WHITE);
        layoutInflater.inflate(layoutResID, frameLayout, true)
        super.setContentView(constraintLayout)
    }

    fun showProgressBar(visibility: Boolean) {
        if (visibility){ println("!!! Satt på progressbar")}else{
            println("!!! Stängt av progressbar")
        }
        progressBar.visibility = if (visibility) View.VISIBLE else View.INVISIBLE
    }
}