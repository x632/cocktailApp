package com.poema.andreasmvvm.activities


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.poema.andreasmvvm.R
import com.poema.andreasmvvm.adapters.DrinksAdapter
import com.poema.andreasmvvm.dataclasses.Drink
import com.poema.andreasmvvm.viewmodel.DrinksViewModel
import kotlinx.android.synthetic.main.activity_main.*


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

       /* val myViewModel = ViewModelProviders.of(this, DrinksViewModelFactory(this,letter)).get(DrinksViewModel::class.java)

        setObserver(myViewModel)*/
        val viewModel = DrinksViewModel(this@MainActivity,"")
        setErrStringObserver(viewModel)
        initSearch()
    }

    fun initSearch(){
        val searchView = findViewById<SearchView>(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                showProgressBar(true)
                val newInstance = DrinksViewModel(this@MainActivity,p0!!)
                setObserver(newInstance)
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })
    }
    private fun setObserver(newInstance: DrinksViewModel){
        newInstance.listData.observe(this@MainActivity, { t ->
            listDrinks.clear()
            t?.let { listDrinks.addAll(it) }
            adapter.notifyDataSetChanged()
            showProgressBar(false)
        })
    }

    private fun setErrStringObserver(viewModel:DrinksViewModel){
        viewModel.otherData.observe(this@MainActivity, { t->
           errorMessage = t
            if (errorMessage != ""){
                Toast.makeText(this,errorMessage, Toast.LENGTH_SHORT
                ).show()
                showProgressBar(false)

            }
        })
    }
}

