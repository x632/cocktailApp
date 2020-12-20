package com.poema.andreasmvvm.viewmodel

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.poema.andreasmvvm.database.AppDatabase
import com.poema.andreasmvvm.dataclasses.Drink
import com.poema.andreasmvvm.repositories.Repository
import com.poema.andreasmvvm.utils.Utility.isInternetAvailable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext


class MainViewModel(context:Context) : ViewModel() {



    private val _letter: MutableLiveData<String> = MutableLiveData()


    private val listData: LiveData<ArrayList<Drink>>? = Transformations.switchMap(_letter) {
        if (context.isInternetAvailable() && it.length < 2 && it.isNotEmpty()) {
            Repository.getMutableLiveData(it)
        } else if (context.isInternetAvailable() && it.length>1 && it != "fav") {
            Repository.otherFunction(it)
        } else {
            Repository.otherFunction(it)
        }
    }

    private val otherData: MutableLiveData<String> = Transformations.switchMap(_letter) {
        Repository.getErrorMessage()
    } as MutableLiveData<String>

    private val iConnection: MutableLiveData<Boolean> = Transformations.switchMap(_letter) {
        Repository.getiConnection()
    } as MutableLiveData<Boolean>



    fun getData(): LiveData<ArrayList<Drink>>? {
        return listData
    }
    fun getString(): MutableLiveData<String>{
        return otherData
    }
    fun getBoolean(): MutableLiveData<Boolean>{
        return iConnection
    }


    fun setLetter(letter: String){
        _letter.value = letter
    }

}

/*
   init {
       val drinkRepository: Repository by lazy {
           Repository
       }
       //iConnection.value = true
       otherData = drinkRepository.errMessString
       }*/

/*
        if (context.isInternetAvailable() && letter.length < 2 && letter.length > 0) {
            listData = drinkRepository.getMutableLiveData(letter)
        } else if (context.isInternetAvailable() && letter.length > 1){
            listData = drinkRepository.otherFunction(letter)
        } else{
            iConnection.value = false

            println("!!!! Värdet har ändrats till (fr viewmodel): ${iConnection.value}")
        }
    }
*/