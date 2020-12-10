package com.poema.andreasmvvm.viewmodel

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.poema.andreasmvvm.activities.BaseActivity
import com.poema.andreasmvvm.dataclasses.Drink
import com.poema.andreasmvvm.dataclasses.Drinks
import com.poema.andreasmvvm.repositories.Repository
import com.poema.andreasmvvm.utils.Datamanager
import com.poema.andreasmvvm.utils.Utility.isInternetAvailable
import java.sql.DatabaseMetaData


class DrinksViewModel(context:Context, letter:String) : ViewModel() {


    private var listData = MutableLiveData<ArrayList<Drink>>()
    private var otherData = MutableLiveData<String>()

    init {
        val drinkRepository: Repository by lazy {
            Repository
        }
        otherData = drinkRepository.errMessString
        if (context.isInternetAvailable() && letter.length < 2 && letter.length > 0) {
            listData = drinkRepository.getMutableLiveData(letter)
        } else if (context.isInternetAvailable() && letter.length > 1){
            listData = drinkRepository.otherFunction(letter)
        }
    }

    fun getData(): MutableLiveData<ArrayList<Drink>> {
        return listData
    }
    fun getString(): MutableLiveData<String>{
        return otherData
    }
}

