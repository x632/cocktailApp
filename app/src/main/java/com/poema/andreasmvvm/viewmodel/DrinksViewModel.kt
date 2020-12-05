package com.poema.andreasmvvm.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.poema.andreasmvvm.activities.BaseActivity
import com.poema.andreasmvvm.dataclasses.Drink
import com.poema.andreasmvvm.dataclasses.Drinks
import com.poema.andreasmvvm.repositories.Repository
import com.poema.andreasmvvm.utils.Utility.isInternetAvailable


class DrinksViewModel(context: Context, letter:String) : ViewModel() {

    private var listData = MutableLiveData<ArrayList<Drink>>()
    
        //skapar en singleton instans av Repository
    init {
        val drinkRepository: Repository by lazy {
            Repository
        }
        if (context.isInternetAvailable()) {
            listData = drinkRepository.getMutableLiveData(letter,context)
        }
    }

    fun getData(context:Context): MutableLiveData<ArrayList<Drink>> {
        return listData
    }
}