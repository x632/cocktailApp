package com.poema.andreasmvvm.dataclasses
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Drinks(
    val drinks: List<Drink>
)
@Entity
data class Drink(
    @PrimaryKey
    val dateModified: String,
    val idDrink: String? = null,
    val strAlcoholic: String? = null,
    val strCategory: String? = null,
    val strCreativeCommonsConfirmed: String? = null,
    val strDrink: String? = null,
    val strDrinkThumb: String? = null,
    val strGlass: String? = null,
    val strIngredient1: String? = null,
    val strIngredient10: String? = null,
    val strIngredient2: String? = null,
    val strIngredient3: String? = null,
    val strIngredient4: String? = null,
    val strIngredient5: String? = null,
    val strIngredient6: String? = null,
    val strIngredient7: String? = null,
    val strIngredient8: String? = null,
    val strIngredient9: String? = null,
    val strInstructions: String? = null,
    val strMeasure1: String? = null,
    val strMeasure10: String? = null,
    val strMeasure2: String? = null,
    val strMeasure3: String? = null,
    val strMeasure4: String? = null,
    val strMeasure5: String? = null,
    val strMeasure6: String? = null,
    val strMeasure7: String? = null,
    val strMeasure8: String? = null,
    val strMeasure9: String? = null,
)