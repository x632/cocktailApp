package com.poema.andreasmvvm.activities


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.poema.andreasmvvm.R
import com.poema.andreasmvvm.adapters.DrinksAdapter
import com.poema.andreasmvvm.database.AppDatabase
import com.poema.andreasmvvm.dataclasses.Drink
import com.poema.andreasmvvm.dataclasses.RoomArray
import com.poema.andreasmvvm.utils.Datamanager
import com.poema.andreasmvvm.utils.Utility.isInternetAvailable
import com.poema.andreasmvvm.viewmodel.MainViewModel
import com.poema.andreasmvvm.viewmodel.DrinksViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.CoroutineContext

class MainActivity() : BaseActivity(), CoroutineScope {

    private lateinit var job: Job
    private lateinit var db: AppDatabase
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    private lateinit var myViewModel: MainViewModel
    private lateinit var listDrinks: MutableList<Drink>
    private lateinit var adapter: DrinksAdapter
    private var errorMessage: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerview.layoutManager = LinearLayoutManager(this@MainActivity)
        listDrinks = mutableListOf()
        job = Job()
        db = DrinksRoom.getInstance(this)
        adapter = DrinksAdapter(
            this,
            listDrinks
        )

        recyclerview.adapter = adapter

        //val myViewModel = ViewModelProvider(this).get(MainViewModel::class.java) // inte möjligt att få in medlemsvariabler till viewmodel med denna
        myViewModel = ViewModelProviders.of(this, DrinksViewModelFactory(this@MainActivity))
            .get(MainViewModel::class.java)
        setErrStringObserver()
        setObserver()//observerar huvuddatat
        initSearch()
        setConnectionObserver()
    }

    private fun setErrStringObserver() {
        myViewModel.getString().observe(this@MainActivity, { t ->
            errorMessage = t
            if (errorMessage != "") {
                Toast.makeText(
                    this, errorMessage, Toast.LENGTH_SHORT
                ).show()
                showProgressBar(false)
            }
        })
    }
    private fun setConnectionObserver() {
        myViewModel.getBoolean().observe(this@MainActivity, { t ->
            val connection = t
            if (connection == false) {
                showProgressBar(false)
            }
            println("!!!! Internetstatus har ändrats (fr MainActivity: $connection")
        })
    }
    //huvudfunktionen
    private fun initSearch() {
        val searchView = findViewById<SearchView>(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(letter: String?): Boolean {
                if (letter!!.isEmpty()) return false
                else {
                    showProgressBar(true)
                    val conn : Boolean = (this@MainActivity.isInternetAvailable())
                    if (!conn){
                    getCashedDrinks(letter)
                        return false
                    }
                    else{
                    myViewModel.setLetter(letter)
                    }
                    return false
                }
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }

    private fun setObserver() {
        myViewModel.getData()?.observe(this@MainActivity, { t ->
            listDrinks.clear()
            Datamanager.drinks.clear()
            t.let { it -> Datamanager.drinks.addAll(it) }
            t.let { it -> listDrinks.addAll(it) }
            adapter.notifyDataSetChanged()
            showProgressBar(false)
            if (Datamanager.drinks.isEmpty()) {
                println("!!! Inte hittat nått!")
            } else {
                //deleteCache()
                createCache()
            }
        })
    }

    private fun createCache(){

        async(Dispatchers.IO) {
            for (i in 0 until listDrinks.size) {
                val id = "${listDrinks[i].idDrink}"
                val numb = db.drinkDao().findDrinkById(id)       //insert(listDrinks[i])
                if (numb == null) {
                    val savedDrinkNum = db.drinkDao().insert(listDrinks[i])
                    println("!!! Drink: ${listDrinks[i].strDrink} with number $savedDrinkNum is saved in cache. number is $i")
                    println("!!! Arraysize is :${listDrinks.size}")
                } else {
                    println("!!! Drink: ${listDrinks[i].strDrink} is already in cache number is $i")
                    println("!!! Arraysize is :${listDrinks.size}")
                }
            }
        }
    }

    private fun checkStringOnCache(str:String) {
        if (str.length in 1..1){
           serachCacheByLetter(str)
        }
        else {
            searchCacheByName(str)
        }
    }

    private fun searchCacheByName(str:String) {
        var str1 = str.decapitalize(Locale.ROOT)
        listDrinks.clear()
        Datamanager.drinks.clear()
        for (drink in RoomArray.drinks){
            var str2 = drink.strDrink!!
            str2 = str2.decapitalize(Locale.ROOT)
            if(str2.contains(str1)){
                listDrinks.add(drink)
                Datamanager.drinks.add(drink)
            }
            adapter.notifyDataSetChanged()
            showProgressBar(false)
        }
    }

    private fun serachCacheByLetter(str:String) {
        listDrinks.clear()
        Datamanager.drinks.clear()
        var str1 = str.decapitalize(Locale.ROOT)
        for (drink in RoomArray.drinks) {
            var str2 = drink.strDrink!!.slice(0..0)
            str2 = str2.decapitalize(Locale.ROOT)
            if (str2 == str1) {
                listDrinks.add(drink)
                Datamanager.drinks.add(drink)
            }
        }
        adapter.notifyDataSetChanged()
        RoomArray.drinks.clear()
        showProgressBar(false)
    }

    fun getCashedDrinks(str:String) {
        RoomArray.drinks.clear()
        val allDrinks  = getAllDrinks()
        launch {
            allDrinks.await().forEach {
                println("!!! Drink in cashe : ${it.strDrink}")
                RoomArray.drinks.add(it)
            }
            sortArray(str)
        }
    }

    private fun sortArray(str:String) {
        val newList : MutableList<String> = mutableListOf()
        val tempList : MutableList<Drink> = mutableListOf()
        // skapar en lista med bara drinknamnen
       for (drink in RoomArray.drinks){
            newList.add(drink.strDrink!!)
            }
        // tar emot den sorterade listan med drinknamn (strängar)
        val theSortedDrinks : MutableList<String> = mainSort(newList)
        //går igenom varje cashad drink mot hela listan med sorterade drinknamn
        // om den hittar samma namn som i drinkobjektet så lägger den dem i tempListan.
        for (i in 0 until RoomArray.drinks.size){
            for (j in 0 until RoomArray.drinks.size) {
                if (theSortedDrinks[i] == RoomArray.drinks[j].strDrink!!) {
                    tempList.add(RoomArray.drinks[j])
                }
            }
        }
        //tömmer cashade listan och skapar den igen med de nu sorterade drinkobjekten
        RoomArray.drinks.clear()
        RoomArray.drinks = tempList
        checkStringOnCache(str)
    }

    private fun getAllDrinks() : Deferred<List<Drink>> =
        async(Dispatchers.IO) {
            db.drinkDao().getAllDrinks()
        }

    private suspend fun switchToMain(){
        withContext(Dispatchers.Main){
           // går till huvudtråden i allDrinksAwait så denna funktion kommer nog inte behövas
       }
    }

    private fun deleteCache(){
        async(Dispatchers.IO) {
            db.drinkDao().deleteAll()
        }
        println("!!! All deleted!")
    }

    private fun mainSort(arr: MutableList<String>):MutableList<String> {
        val sortedArray : MutableList<String> = mutableListOf()
        val arraySize = arr.size
        sortStrings(arr, arraySize)
        for (i in 0 until arraySize) {
            sortedArray.add(arr[i])
        }
        return sortedArray
    }

    private fun sortStrings(arr: MutableList<String>, arraySize: Int) {
        var temp: String

        for (j in 0 until arraySize - 1) {
            for (i in j + 1 until arraySize) {
                if (arr[j] > arr[i]) {
                    temp = arr[j]
                    arr[j] = arr[i]
                    arr[i] = temp
                }
            }
        }
    }

}


