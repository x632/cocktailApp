package com.poema.andreasmvvm.activities


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.poema.andreasmvvm.R
import com.poema.andreasmvvm.adapters.DrinksAdapter
import com.poema.andreasmvvm.dataclasses.Drink
import com.poema.andreasmvvm.utils.Datamanager
import com.poema.andreasmvvm.viewmodel.DrinksViewModel
import com.poema.andreasmvvm.viewmodel.DrinksViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    private lateinit var listDrinks: MutableList<Drink>
    private lateinit var adapter: DrinksAdapter
    private var errorMessage: String = ""
    private var letter = "a"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerview.layoutManager = LinearLayoutManager(this@MainActivity)
        listDrinks = mutableListOf()
        adapter = DrinksAdapter(this,
                listDrinks
        )

        recyclerview.adapter = adapter

       val myViewModel = ViewModelProviders.of(this, DrinksViewModelFactory(this@MainActivity,letter)).get(DrinksViewModel::class.java)

        setErrStringObserver(myViewModel)
        setObserver(myViewModel)
        initSearch(myViewModel)
    }

    fun initSearch(viewModel:DrinksViewModel){
        val searchView = findViewById<SearchView>(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                showProgressBar(true)
                val newInstance = DrinksViewModel(this@MainActivity, p0!!)
                setObserver(newInstance)
                setConnectionObserver(newInstance)
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })
    }
    private fun setObserver(newInstance: DrinksViewModel){
        newInstance.getData().observe(this@MainActivity, { t ->
            listDrinks.clear()
            Datamanager.drinks.clear()
            t?.let {it -> Datamanager.drinks.addAll(it)}
            t?.let { it -> listDrinks.addAll(it)}
            adapter.notifyDataSetChanged()
            showProgressBar(false)
        })
    }

    private fun setErrStringObserver(viewModel:DrinksViewModel){
        viewModel.getString().observe(this@MainActivity, { t->
           errorMessage = t
            if (errorMessage != ""){
                Toast.makeText(this,errorMessage, Toast.LENGTH_SHORT
                ).show()
                showProgressBar(false)
            }
        })
    }
    private fun setConnectionObserver(viewModel:DrinksViewModel){
        viewModel.iConnection.observe(this@MainActivity, { t->
            val connection = t
            showProgressBar(false)
            println("!!!! Intrnetstatus har ändrats (fr MainActivity: $connection")
        })
    }
}


