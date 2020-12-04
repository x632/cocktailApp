package com.poema.andreasmvvm.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.poema.andreasmvvm.R
import com.poema.andreasmvvm.adapters.DrinksAdapter
import com.poema.andreasmvvm.dataclasses.Drink
import com.poema.andreasmvvm.viewmodel.DrinksViewModel
import com.poema.andreasmvvm.viewmodel.DrinksViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var listDrinks: MutableList<Drink>
    private lateinit var adapter: DrinksAdapter
    var letter = "c"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerview.layoutManager = LinearLayoutManager(this@MainActivity)
        listDrinks = mutableListOf()
        adapter = DrinksAdapter(this,
                listDrinks
        )
        recyclerview.adapter = adapter
        val myViewModel = ViewModelProviders.of(this, DrinksViewModelFactory(this,letter)).get(DrinksViewModel::class.java)

        setObserver(myViewModel)
        initSearchView()
    }

    fun initSearchView(){
        val searchView = findViewById<SearchView>(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                showProgressBar(true)
                letter = p0!!
                val a = DrinksViewModel(this@MainActivity,letter)
                setObserver(a)
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })
    }
    fun setObserver(a:DrinksViewModel){
        a.getData().observe(this@MainActivity, { t ->
            listDrinks.clear()
            t?.let { listDrinks.addAll(it) }
            adapter.notifyDataSetChanged()
            showProgressBar(false)
        })

    }
}

