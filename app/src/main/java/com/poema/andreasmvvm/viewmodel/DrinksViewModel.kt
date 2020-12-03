package com.poema.andreasmvvm.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.poema.andreasmvvm.dataclasses.Drink
import com.poema.andreasmvvm.repositories.UserRepository
import com.poema.andreasmvvm.utils.Utility.isInternetAvailable


class DrinksViewModel(private val context: Context) : ViewModel() {

    //skapar tom (mutable) array av users som är livedata
    private var listData = MutableLiveData<ArrayList<Drink>>()

    //tar in en instans av UserRepository
    init {
        val userRepository: UserRepository by lazy {
            UserRepository
        }
        if (context.isInternetAvailable()) {

            listData = userRepository.getMutableLiveData(context)
        }
    }

    fun getData(): MutableLiveData<ArrayList<Drink>> {
        return listData
    }
}