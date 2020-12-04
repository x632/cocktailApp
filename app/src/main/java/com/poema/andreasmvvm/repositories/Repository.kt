package com.poema.andreasmvvm.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.poema.andreasmvvm.api.ApiClient
import com.poema.andreasmvvm.dataclasses.Drinks
import com.poema.andreasmvvm.dataclasses.Drink
import com.poema.andreasmvvm.utils.Utility.hideProgressBar
import com.poema.andreasmvvm.utils.Utility.showProgressBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object Repository {

    fun getMutableLiveData(context: Context,letter:String) : MutableLiveData<ArrayList<Drink>>{

        val mutableLiveData = MutableLiveData<ArrayList<Drink>>()



        ApiClient.apiService.getDrinksByLetter(letter).enqueue(object : Callback<Drinks> {
            override fun onFailure(call: Call<Drinks>, t: Throwable) {
                Log.e("error", t.localizedMessage!!)
            }

            override fun onResponse(
                call: Call<Drinks>,
                response: Response<Drinks>
            ) {
                val drinksResponse = response.body()
                val myDrinks : Drinks? = drinksResponse
                val tempArray : MutableList<Drink> = mutableListOf()
                for (drink in myDrinks?.drinks!!){
                    //println("!!! article: $article \n\n")
                        tempArray.add (drink)
                    println("!!! Drinkobjektets parametrar: $drink")

                    }
                mutableLiveData.value = tempArray as ArrayList<Drink>
            }
        })

        return mutableLiveData
    }

}