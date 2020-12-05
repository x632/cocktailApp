package com.poema.andreasmvvm.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.poema.andreasmvvm.api.ApiClient
import com.poema.andreasmvvm.dataclasses.Drinks
import com.poema.andreasmvvm.dataclasses.Drink
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object Repository {

    fun getMutableLiveData(letter:String) : MutableLiveData<ArrayList<Drink>>{

        val mutableLiveData = MutableLiveData<ArrayList<Drink>>()

        ApiClient.apiService.getDrinksByLetter(letter).enqueue(object : Callback<Drinks> {
            override fun onFailure(call: Call<Drinks>, t: Throwable) {
                println("!!!HAR VARIT I ON FAILURE!!!")
                Log.e("error", t.localizedMessage!!)
            }

            override fun onResponse(
                call: Call<Drinks>,
                response: Response<Drinks>
            ) {
                val drinksResponse = response.body()

                    val myDrinks: Drinks? = drinksResponse
                    val tempArray: MutableList<Drink> = mutableListOf()
                    if (myDrinks?.drinks == null){println("!!! No Drinks!!!!")
                        }
                else {
                        for (drink in myDrinks?.drinks!!) {
                            tempArray.add(drink)
                            println("!!! Drinkobjektets parametrar: $drink")
                        }
                    }
                    mutableLiveData.value = tempArray as ArrayList<Drink>

            }
        })
        return mutableLiveData
    }
}