package com.poema.andreasmvvm.activities


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.poema.andreasmvvm.R
import com.poema.andreasmvvm.adapters.DrinksAdapter
import com.poema.andreasmvvm.dataclasses.Drink
import com.poema.andreasmvvm.utils.Datamanager
import com.poema.andreasmvvm.viewmodel.MainViewModel
import com.poema.andreasmvvm.viewmodel.DrinksViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*


class MainActivity : BaseActivity() {

    private lateinit var listDrinks: MutableList<Drink>
    private lateinit var adapter: DrinksAdapter
    private var errorMessage: String = ""

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
        val myViewModel = ViewModelProviders.of(this, DrinksViewModelFactory(this@MainActivity)).get(MainViewModel::class.java)
        setErrStringObserver(myViewModel)
        setObserver(myViewModel)
        initSearch(myViewModel)
        setConnectionObserver(myViewModel)
    }

    fun initSearch(viewModel:MainViewModel){
        val searchView = findViewById<SearchView>(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                showProgressBar(true)
                viewModel.setLetta(p0!!)
                /*val newInstance = MainViewModel(this@MainActivity)*/
                //setObserver(viewModel)
                //setConnectionObserver(viewModel)
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }
    private fun setObserver(newInstance: MainViewModel){
        newInstance.getData()?.observe(this@MainActivity, { t ->
            listDrinks.clear()
            Datamanager.drinks.clear()
            t?.let {it -> Datamanager.drinks.addAll(it)}
            t?.let { it -> listDrinks.addAll(it)}
            adapter.notifyDataSetChanged()
            showProgressBar(false)
        })
    }

    private fun setErrStringObserver(viewModel:MainViewModel){
        viewModel.getString().observe(this@MainActivity, { t->
           errorMessage = t
            if (errorMessage != ""){
                Toast.makeText(this,errorMessage, Toast.LENGTH_SHORT
                ).show()
                showProgressBar(false)
            }
        })
    }
    private fun setConnectionObserver(viewModel:MainViewModel){
        viewModel.getBoolean().observe(this@MainActivity, { t->
            val connection = t
            showProgressBar(false)
            println("!!!! Intrnetstatus har ändrats (fr MainActivity: $connection")
        })
    }
}



