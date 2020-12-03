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

object UserRepository {

    fun getMutableLiveData(context: Context) : MutableLiveData<ArrayList<Drink>>{

        val mutableLiveData = MutableLiveData<ArrayList<Drink>>()

        context.showProgressBar()
        //ApiClient.apiService.getUsers().enqueue(object : Callback<MutableList<Drinks>> {
        ApiClient.apiService.getDrinksByLetter("l").enqueue(object : Callback<Drinks> {
            override fun onFailure(call: Call<Drinks>, t: Throwable) {
                hideProgressBar()
                Log.e("error", t.localizedMessage!!)
            }

            override fun onResponse(
                call: Call<Drinks>,
                response: Response<Drinks>
            ) {
                hideProgressBar()
                val drinksResponse = response.body()
                val myDrinks : Drinks? = drinksResponse
                val tempArray : MutableList<Drink> = mutableListOf()
                for (drink in myDrinks?.drinks!!){
                    //println("!!! article: $article \n\n")
                        tempArray.add (drink)
                    println("!!! This is the drinks all parameters: $drink")

                    }
                mutableLiveData.value = tempArray as ArrayList<Drink>
            }
                //usersResponse?.let { mutableLiveData.value = it as ArrayList<User> }

            //}

        })

        return mutableLiveData
    }

}