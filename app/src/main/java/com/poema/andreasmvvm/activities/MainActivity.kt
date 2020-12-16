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
import com.poema.andreasmvvm.utils.Datamanager
import com.poema.andreasmvvm.viewmodel.MainViewModel
import com.poema.andreasmvvm.viewmodel.DrinksViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class MainActivity() : BaseActivity(),CoroutineScope {

    private lateinit var job: Job
    private lateinit var db: AppDatabase
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    lateinit private var myViewModel: MainViewModel
    private lateinit var listDrinks: MutableList<Drink>
    private lateinit var adapter: DrinksAdapter
    private var errorMessage: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerview.layoutManager = LinearLayoutManager(this@MainActivity)
        listDrinks = mutableListOf()
        job = Job()
        db = DrinksRoom.getInstance(applicationContext)
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

    fun initSearch() {
        val searchView = findViewById<SearchView>(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0!!.isEmpty()) return false
                else {
                    showProgressBar(true)
                    myViewModel.setLetta(p0)
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
                //showCashedDrinks()
               compareWithCashe(0)
            }
        })
    }

    private fun compareWithCashe(counter: Int) {
        val lengthOfArray = Datamanager.drinks.size
        println("Length of Array: $lengthOfArray, counter: $counter")
        if (counter < Datamanager.drinks.size) {
            val drinkId = "${Datamanager.drinks[counter].idDrink}"
            val drink = Datamanager.drinks[counter]
            checkIfDrinkAlreadyCashed(drinkId, drink, counter)
        }

    }

    fun checkIfDrinkAlreadyCashed(id: String, drinkObject: Drink, counter: Int) {
        var drink: Drink?
        async(Dispatchers.IO)
        {
            drink = db.drinkDao().findDrinkById(id)
            if (drink == null) {
                val numb1 = db.drinkDao().insert(drinkObject)
                println("!!! Did not find the drink (number: $numb1) in cash so it was cashed now")
                switchToMain(counter)
            }
            else {
                println("!!! Drink already cached")

                switchToMain(counter)
            }
        }

    }

    private suspend fun switchToMain(counter: Int){
        var counter2 = counter
        withContext(Dispatchers.Main){
            //println("!!!Varit i c handlern ")
            counter2 ++
            compareWithCashe(counter2)
        }
    }

    private fun getAllDrinks() : Deferred<List<Drink>> =
        async(Dispatchers.IO) {
            db.drinkDao().getAllDrinks()
        }

    fun showCashedDrinks() {
        println("!!! Varit i showCashedDrinks")
        val allDrinks  = getAllDrinks()
        launch {
            allDrinks.await().forEach {
              println("!!! Drink in cashe : ${it.strDrink}")
            }
        }
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
    private fun deleteEverything(){
        async(Dispatchers.IO) {db.drinkDao().deleteAll()}
        println("!!! All deleted!")
    }

/*    fun room() {
        println("!!! emil enter room function")

            val drink0 = Datamanager.drinks[0]
            val drink1 = Datamanager.drinks[1]


            GlobalScope.launch {
               db.drinkDao().deleteAll()
               val numb1 = db.drinkDao().insert(drink0)
                val numb2 = db.drinkDao().insert(drink1)
                println("!!! Numren i databasen: $numb1 och $numb2")
             val drinks: List<Drink> = db.drinkDao().getAllDrinks()
               for (drink in drinks) {
                   println("!!! Drinks from room name: ${drink.strDrink} and drink id: ${drink.idDrink}")
                }
            }
        }*/
}


