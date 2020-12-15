package com.poema.andreasmvvm.activities


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apicall.db.AppDatabase
import com.example.apicall.db.TestDrink
import com.poema.andreasmvvm.R
import com.poema.andreasmvvm.adapters.DrinksAdapter
import com.poema.andreasmvvm.dataclasses.Drink
import com.poema.andreasmvvm.utils.Datamanager
import com.poema.andreasmvvm.viewmodel.MainViewModel
import com.poema.andreasmvvm.viewmodel.DrinksViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*


class MainActivity : BaseActivity() {

    lateinit private var myViewModel: MainViewModel
    private lateinit var listDrinks: MutableList<Drink>
    private lateinit var adapter: DrinksAdapter
    private var errorMessage: String = ""

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerview.layoutManager = LinearLayoutManager(this@MainActivity)
        listDrinks = mutableListOf()
        adapter = DrinksAdapter(this,
                listDrinks
        )

        recyclerview.adapter = adapter

       //val myViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        myViewModel = ViewModelProviders.of(this, DrinksViewModelFactory(this@MainActivity)).get(MainViewModel::class.java)
        setErrStringObserver()
        setObserver()
        initSearch()
        setConnectionObserver()

        db = DrinksRoom.getInstance(applicationContext)
        room()
    }

    fun initSearch(){
        val searchView = findViewById<SearchView>(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if(p0!!.isEmpty())return false
                else{
                showProgressBar(true)
                myViewModel.setLetta(p0!!)
                return false}

            }
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }

    private fun setObserver(){
        myViewModel.getData()?.observe(this@MainActivity, { t ->
            listDrinks.clear()
            Datamanager.drinks.clear()
            t.let { it -> Datamanager.drinks.addAll(it)}
            t.let { it -> listDrinks.addAll(it)}
            adapter.notifyDataSetChanged()
            showProgressBar(false)
        })
    }

    private fun setErrStringObserver(){
        myViewModel.getString().observe(this@MainActivity, { t->
           errorMessage = t
            if (errorMessage != ""){
                Toast.makeText(this,errorMessage, Toast.LENGTH_SHORT
                ).show()
                showProgressBar(false)
            }
        })
    }
    private fun setConnectionObserver(){
        myViewModel.getBoolean().observe(this@MainActivity, { t->
            val connection = t
            if (connection == false){
            showProgressBar(false)}
            println("!!!! Internetstatus har ändrats (fr MainActivity: $connection")
        })
    }

    fun room() {
        println("emil enter room function")

        val drink2 = TestDrink("vodka", 5, true)
        val drink1 = TestDrink("gin", 19, true)
        val drink3 = TestDrink("sprite", 1, false)

        println("emil created the drinks")

        GlobalScope.launch {
            db.drinkDao().deleteAll()
            db.drinkDao().insert(drink1)
            // db.drinkDao().delete(drink1)
            val drinks: List<TestDrink> = db.drinkDao().getAllDrinks()
            for (drink in drinks) {
                println("emil get all drinks ${drink}")
            }
        }
    }
}



