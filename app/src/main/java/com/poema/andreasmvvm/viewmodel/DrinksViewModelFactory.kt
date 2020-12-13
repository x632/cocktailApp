package com.poema.andreasmvvm.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class DrinksViewModelFactory (val context: Context) : ViewModelProvider.NewInstanceFactory() {

   override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(context) as T
    }

}
