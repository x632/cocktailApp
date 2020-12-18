package com.poema.andreasmvvm.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.poema.andreasmvvm.api.ApiClient
import com.poema.andreasmvvm.dataclasses.Drinks
import com.poema.andreasmvvm.dataclasses.Drink
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object Repository {


    //var job: CompletableJob? = null
    var errMessString = MutableLiveData<String>()
    var iConnection = MutableLiveData<Boolean>()
    fun getMutableLiveData(letter: String): MutableLiveData<ArrayList<Drink>> {
        iConnection.value = true
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
                if (myDrinks?.drinks == null) {
                    errMessString.value = "There are no drinks starting with that letter!"
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

    fun otherFunction(letter: String): MutableLiveData<ArrayList<Drink>> {
        iConnection.value = true
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
                if (myDrinks?.drinks == null) {
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

    fun getErrorMessage(): MutableLiveData<String> {
        return errMessString
    }

    fun getiConnection(): MutableLiveData<Boolean> {
        return iConnection
    }
}
    /*fun getDrinks(letter: String): MutableLiveData<ArrayList<Drink>> {
        job = Job()
        return object: MutableLiveData<ArrayList<Drink>>(){
            override fun onActive() {
                super.onActive()
                job?.let{ theJob ->
                    CoroutineScope(IO + theJob).launch {
                        val drinkArray = getCashedDrinks(letter)
                        //val user = MyRetrofitBuilder.apiService.getUser(userId)
                        withContext(Main){
                            value = drinkArray
                            theJob.complete()
                        }
                    }

                }

            }
        }
    }

    fun cancelJobs(){
        job?.cancel()
    }
    fun getCashedDrinks(letter:String):ArrayList<Drink> {
        var drinksArr : MutableList<Drink> = mutableListOf()
        val allDrinks  = getAllDrinks()
        launch {
            allDrinks.await().forEach {
                println("!!! Drink in cashe : ${it.strDrink}")

            RoomArray.drinks.add(it)

            }
            drinksArr = RoomArray.drinks as ArrayList<Drink>
        }
        return drinksArr
    }

    private fun getAllDrinks() : Deferred<List<Drink>> =
        async(Dispatchers.IO) {
            db.drinkDao().getAllDrinks()
        }

}*/
