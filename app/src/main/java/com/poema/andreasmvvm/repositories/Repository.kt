package com.poema.andreasmvvm.repositories

import android.content.Context
import android.util.Log
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.lifecycle.MutableLiveData
import com.poema.andreasmvvm.api.ApiClient
import com.poema.andreasmvvm.dataclasses.Drinks
import com.poema.andreasmvvm.dataclasses.Drink
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object Repository {

    fun getMutableLiveData(letter:String,context: Context) : MutableLiveData<ArrayList<Drink>>{

        val mutableLiveData = MutableLiveData<ArrayList<Drink>>()
        //val errMessString = LiveData<String>

        ApiClient.apiService.getDrinksByLetter(letter).enqueue(object : Callback<Drinks> {
            override fun onFailure(call: Call<Drinks>, t: Throwable) {
                println("!!!HAR VARIT I ON FAILURE!!!")
                Log.e("error", t.localizedMessage!!)
                onError(t)
                //errMessString.value = t.localizedMessage!!
                makeText(context,t.localizedMessage!!, Toast.LENGTH_LONG
                ).show()
            }

            override fun onResponse(
                call: Call<Drinks>,
                response: Response<Drinks>
            ) {
                val drinksResponse = response.body()

                    val myDrinks: Drinks? = drinksResponse
                    val tempArray: MutableList<Drink> = mutableListOf()
                    if (myDrinks?.drinks == null){
                        makeText(context,"There are no drinks starting with that letter!", Toast.LENGTH_LONG
                        ).show()
                        } else {
                        for (drink in myDrinks.drinks) {
                            tempArray.add(drink)
                            //println("!!! Drinkobjektets parametrar: $drink")
                        }
                    }
                    mutableLiveData.value = tempArray as ArrayList<Drink>

                }
        })
        return mutableLiveData
    }
    fun otherFunction(letter:String, context: Context):MutableLiveData<ArrayList<Drink>>{
        val mutableLiveData = MutableLiveData<ArrayList<Drink>>()
        ApiClient.apiService.getDrinksByName(letter).enqueue(object : Callback<Drinks> {
            override fun onFailure(call: Call<Drinks>, t: Throwable) {
                Log.e("error", t.localizedMessage!!)
                //errMessString.value = t.localizedMessage!!
                makeText(context,t.localizedMessage!!, Toast.LENGTH_LONG
                ).show()
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
                    makeText(context,"There are no drinks with those letters!", Toast.LENGTH_LONG
                    ).show()
                } else {
                    for (drink in myDrinks.drinks) {
                        tempArray.add(drink)
                    }
                }
                mutableLiveData.value = tempArray as ArrayList<Drink>
            }
        })
        return mutableLiveData

    }
    fun onError(t: Throwable) {
        //set timer
        //go again 3 times
    }
}