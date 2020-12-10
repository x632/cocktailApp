package com.poema.andreasmvvm.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.poema.andreasmvvm.R
import com.poema.andreasmvvm.utils.Datamanager
import kotlinx.android.synthetic.main.activity_chosen_drink.*

class ChosenDrinkActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chosen_drink)
        val pos = intent.getIntExtra("pos",0)
        val drinkName = Datamanager.drinks[pos].strDrink
        tv_name.text = drinkName
        showImage(pos)
        getNotNullIngredients(pos)
        showInstructions(pos)
        getNotNullMeasurements(pos)
    }

    private fun getNotNullIngredients(pos:Int) {
        var ingredStr = ""
        if (Datamanager.drinks[pos].strIngredient1 != null){ ingredStr += "${Datamanager.drinks[pos].strIngredient1}\n"}
        if (Datamanager.drinks[pos].strIngredient2 != null){ ingredStr += "${Datamanager.drinks[pos].strIngredient2}\n"}
        if (Datamanager.drinks[pos].strIngredient3 != null){ ingredStr += "${Datamanager.drinks[pos].strIngredient3}\n"}
        if (Datamanager.drinks[pos].strIngredient4 != null){ ingredStr += "${Datamanager.drinks[pos].strIngredient4}\n"}
        if (Datamanager.drinks[pos].strIngredient5 != null){ ingredStr += "${Datamanager.drinks[pos].strIngredient5}\n"}
        if (Datamanager.drinks[pos].strIngredient6 != null){ ingredStr += "${Datamanager.drinks[pos].strIngredient6}\n"}
        if (Datamanager.drinks[pos].strIngredient7 != null){ ingredStr += "${Datamanager.drinks[pos].strIngredient7}\n"}
        if (Datamanager.drinks[pos].strIngredient8 != null){ ingredStr += "${Datamanager.drinks[pos].strIngredient8}\n"}
        if (Datamanager.drinks[pos].strIngredient9 != null){ ingredStr += "${Datamanager.drinks[pos].strIngredient9}\n"}
        if (Datamanager.drinks[pos].strIngredient10 != null){ ingredStr += "${Datamanager.drinks[pos].strIngredient10}\n"}
        tv_ingred.text = ingredStr
    }

    private fun getNotNullMeasurements(pos:Int) {
        var measurementStr = ""
        if (Datamanager.drinks[pos].strMeasure1 != null) {measurementStr += "${Datamanager.drinks[pos].strMeasure1}\n"}
        if (Datamanager.drinks[pos].strMeasure2 != null) {measurementStr += "${Datamanager.drinks[pos].strMeasure2}\n"}
        if (Datamanager.drinks[pos].strMeasure3 != null) {measurementStr += "${Datamanager.drinks[pos].strMeasure3}\n"}
        if (Datamanager.drinks[pos].strMeasure4 != null) {measurementStr += "${Datamanager.drinks[pos].strMeasure4}\n"}
        if (Datamanager.drinks[pos].strMeasure5 != null) {measurementStr += "${Datamanager.drinks[pos].strMeasure5}\n"}
        if (Datamanager.drinks[pos].strMeasure6 != null) {measurementStr += "${Datamanager.drinks[pos].strMeasure6}\n"}
        if (Datamanager.drinks[pos].strMeasure7 != null) {measurementStr += "${Datamanager.drinks[pos].strMeasure7}\n"}
        if (Datamanager.drinks[pos].strMeasure8 != null) {measurementStr += "${Datamanager.drinks[pos].strMeasure8}\n"}
        if (Datamanager.drinks[pos].strMeasure9 != null) {measurementStr += "${Datamanager.drinks[pos].strMeasure9}\n"}
        if (Datamanager.drinks[pos].strMeasure10 != null) {measurementStr += "${Datamanager.drinks[pos].strMeasure10}\n"}

        tv_measure.text = measurementStr
    }


    private fun showImage(pos: Int) {
        Glide.with(this)
            .load(Datamanager.drinks[pos].strDrinkThumb).apply(RequestOptions.circleCropTransform())
            .into(iv_picture)
    }


    private fun showInstructions(pos: Int){
        tv_instructions.text = Datamanager.drinks[pos].strInstructions
    }


}