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

    var errMessString = MutableLiveData<String>()

    fun getMutableLiveData(letter:String) : MutableLiveData<ArrayList<Drink>>{

        val mutableLiveData = MutableLiveData<ArrayList<Drink>>()


        ApiClient.apiService.getDrinksByLetter(letter).enqueue(object : Callback<Drinks> {
            override fun onFailure(call: Call<Drinks>, t: Throwable) {
                Log.e("error", t.localizedMessage!!)
                errMessString.value = t.localizedMessage!!.toString()
            }

            override fun onResponse(
                call: Call<Drinks>,
                response: Response<Drinks>
            ) {
                val drinksResponse = response.body()

                    val myDrinks: Drinks? = drinksResponse
                    val tempArray: MutableList<Drink> = mutableListOf()
                    if (myDrinks?.drinks == null){
                        errMessString.value = "There are no drinks starting with that letter!"
                        } else {errMessString.value = ""
                        for (drink in myDrinks.drinks) {
                            tempArray.add(drink)
                            println("!!! Drinkobjektets parametrar: $drink")
                        }
                    }
                    mutableLiveData.value = tempArray as ArrayList<Drink>
                }
        })
        return mutableLiveData
    }
    fun otherFunction(letter:String):MutableLiveData<ArrayList<Drink>>{
        val mutableLiveData = MutableLiveData<ArrayList<Drink>>()
        ApiClient.apiService.getDrinksByName(letter).enqueue(object : Callback<Drinks> {
            override fun onFailure(call: Call<Drinks>, t: Throwable) {
                Log.e("error", t.localizedMessage!!)
                errMessString.value = t.localizedMessage!!
            }

            override fun onResponse(
                call: Call<Drinks>,
                response: Response<Drinks>
            ) {
                val drinksResponse = response.body()
                val code = response.code()
                println("!!! Responskoden: ${code}")
                val myDrinks: Drinks? = drinksResponse
                val tempArray: MutableList<Drink> = mutableListOf()
                if (myDrinks?.drinks == null){
                    errMessString.value = "There are no drinks containing those letters!"
                } else {
                    errMessString.value = ""
                    for (drink in myDrinks.drinks) {
                        tempArray.add(drink)
                    }
                }
                mutableLiveData.value = tempArray as ArrayList<Drink>
            }
        })
        return mutableLiveData

    }
}
