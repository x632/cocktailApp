package com.poema.andreasmvvm.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.poema.andreasmvvm.dataclasses.Drink
import com.poema.andreasmvvm.repositories.Repository
import com.poema.andreasmvvm.utils.Utility.isInternetAvailable


class DrinksViewModel(private val context: Context) : ViewModel() {

    //skapar tom (mutable) array av users som är livedata
    private var listData = MutableLiveData<ArrayList<Drink>>()
    private var letter = "q"
    //skapar en instans av Repository
    init {
        val drinkRepository: Repository by lazy {
            Repository
        }
        if (context.isInternetAvailable()) {

            listData = drinkRepository.getMutableLiveData(context,letter)
        }
    }

    fun getData(): MutableLiveData<ArrayList<Drink>> {
        return listData
    }
}