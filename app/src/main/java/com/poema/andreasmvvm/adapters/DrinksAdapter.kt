package com.poema.andreasmvvm.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.poema.andreasmvvm.R
import com.poema.andreasmvvm.dataclasses.Drink


class DrinksAdapter(private val context: Context, private var list: MutableList<Drink>) : RecyclerView.Adapter<DrinksAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.user_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val drink = list[position]
        holder.name.text = drink.strDrink
        val tempStr = "Category : " + drink.strCategory
        holder.info1.text = tempStr
        holder.info2.text = "Ingredients:"
        val tempStr2 = drink.strIngredient1 + ",\n" + drink.strIngredient2 + ",\n" + drink.strIngredient3
        holder.ingredient.text = tempStr2

        Glide.with(holder.itemView.context)
            .load(drink.strDrinkThumb).apply(RequestOptions.circleCropTransform())
            .into(holder.image)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.txt_name)
        val info1: TextView = itemView.findViewById(R.id.txt_category)
        val info2: TextView = itemView.findViewById(R.id.txt_info2)
        val ingredient: TextView = itemView.findViewById(R.id.txt_ingredient)
        val image: ImageView = itemView.findViewById(R.id.imageview_drink)


        init {
               itemView.setOnClickListener {
                   println("!!! Har klickat på position $adapterPosition")
                }
            }
    }
}


