package com.poema.andreasmvvm.dataclasses
import com.google.gson.annotations.SerializedName


data class Drinks(
    val drinks: List<Drink>
)

data class Drink(
    val dateModified: String,
    val idDrink: String,
    val strAlcoholic: String,
    val strCategory: String,
    val strCreativeCommonsConfirmed: String,
    val strDrink: String,
    val strDrinkAlternate: Any,
    val strDrinkDE: Any,
    val strDrinkES: Any,
    val strDrinkFR: Any,
    val strDrinkThumb: String,
    @SerializedName("strDrinkZH-HANS")
    val strDrinkZHHANS: Any,
    @SerializedName("strDrinkZH-HANT")
    val strDrinkZHHANT: Any,
    val strGlass: String,
    val strIBA: Any,
    val strImageAttribution: Any,
    val strImageSource: Any,
    val strIngredient1: String,
    val strIngredient10: Any,
    val strIngredient2: String,
    val strIngredient3: String,
    val strIngredient4: String,
    val strIngredient5: Any,
    val strIngredient6: Any,
    val strIngredient7: Any,
    val strIngredient8: Any,
    val strIngredient9: Any,
    val strInstructions: String,
    val strInstructionsDE: String,
    val strInstructionsES: Any,
    val strInstructionsFR: Any,
    @SerializedName("strInstructionsZH-HANS")
    val strInstructionsZHHANS: Any,
    @SerializedName("strInstructionsZH-HANT")
    val strInstructionsZHHANT: Any,
    val strMeasure1: String,
    val strMeasure10: Any,
    val strMeasure2: String,
    val strMeasure3: String,
    val strMeasure4: String,
    val strMeasure5: Any,
    val strMeasure6: Any,
    val strMeasure7: Any,
    val strMeasure8: Any,
    val strMeasure9: Any,
    val strTags: Any,
    val strVideo: Any
)