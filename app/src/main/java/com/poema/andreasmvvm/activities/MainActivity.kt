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
import kotlin.coroutines.CoroutineContext
import android.content.Context

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

        //val myViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        myViewModel = ViewModelProviders.of(this, DrinksViewModelFactory(this@MainActivity))
            .get(MainViewModel::class.java)
        setErrStringObserver()
        setObserver()
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

    fun initSearch() {
        val searchView = findViewById<SearchView>(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0!!.isEmpty()) return false
                else {
                    showProgressBar(true)
                    val conn : Boolean = (this@MainActivity.isInternetAvailable())
                    if (!conn){
                    getCashedDrinks(p0)
                        return false
                    }
                    else{
                    myViewModel.setLetta(p0)
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
                //deleteEverything()
                createCache()
                //getCashedDrinks()
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

    fun getCashedDrinks(str:String) {
        RoomArray.drinks.clear()
        val allDrinks  = getAllDrinks()
        launch {
            allDrinks.await().forEach {
                println("!!! Drink in cashe : ${it.strDrink}")
                RoomArray.drinks.add(it)
            }
            checkStringOnCache(str)
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
        var str1 = str.decapitalize()
        println("!!! Been in search Cache by name!")
        listDrinks.clear()
        Datamanager.drinks.clear()
        for (drink in RoomArray.drinks){
            var str2 = drink.strDrink!!
            str2 = str2.decapitalize()
            if(str2.contains(str1)){
                listDrinks.add(drink)
                Datamanager.drinks.add(drink)
            }
            adapter.notifyDataSetChanged()
            showProgressBar(false)
        }
    }

    private fun serachCacheByLetter(str:String) {
       println("!!! Been in search cache by letter!")
        listDrinks.clear()
        Datamanager.drinks.clear()
        var str1 = str.decapitalize()
        for (drink in RoomArray.drinks) {
            var str2 = drink.strDrink!!.slice(0..0)
            str2 = str2.decapitalize()
            if (str2 == str1) {
                listDrinks.add(drink)
                Datamanager.drinks.add(drink)
                println("!!! these are the drinks it was supposed to get from Cache: ${drink.strDrink}")
            }
        }
        adapter.notifyDataSetChanged()
        RoomArray.drinks.clear()
        showProgressBar(false)
    }

    private fun getAllDrinks() : Deferred<List<Drink>> =
        async(Dispatchers.IO) {
            db.drinkDao().getAllDrinks()
        }

    private suspend fun switchToMain(){
        withContext(Dispatchers.Main){
           // compareWithCache(counter2)
       }
    }

    private fun deleteEverything(){
        async(Dispatchers.IO) {db.drinkDao().deleteAll()}
        println("!!! All deleted!")
    }


}


