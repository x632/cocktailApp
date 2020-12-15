package com.poema.andreasmvvm.viewmodel

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.poema.andreasmvvm.activities.BaseActivity
import com.poema.andreasmvvm.dataclasses.Drink
import com.poema.andreasmvvm.dataclasses.Drinks
import com.poema.andreasmvvm.repositories.Repository
import com.poema.andreasmvvm.utils.Datamanager
import com.poema.andreasmvvm.utils.Utility.isInternetAvailable
import java.sql.DatabaseMetaData


class MainViewModel(context:Context) : ViewModel() {

    private val _letta: MutableLiveData<String> = MutableLiveData()
    //private val _search: MutableLiveData<String> = MutableLiveData()
    //private var listData = MutableLiveData<ArrayList<Drink>>()
    //private var otherData = MutableLiveData<String>()
    //var iConnection = MutableLiveData<Boolean>()

    val listData: LiveData<ArrayList<Drink>>? = Transformations.switchMap(_letta) {
        if (context.isInternetAvailable() && it.length < 2 && it.length > 0) {
            Repository.getMutableLiveData(it)
        } else if (context.isInternetAvailable() && it.length>1 && it != "fav") {
            Repository.otherFunction(it)
        } else {
            Repository.otherFunction(it)
        }
    }

    val otherData: MutableLiveData<String> = Transformations.switchMap(_letta) {
        Repository.getErrorMessage(it)
    } as MutableLiveData<String>

    val iConnection: MutableLiveData<Boolean> = Transformations.switchMap(_letta) {
        Repository.getiConnection(it)
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


    fun setLetta(letta: String){
        val update = letta
      /* if (_letta.value == update) {
            return
        }*/
        _letta.value = update
    }
}

