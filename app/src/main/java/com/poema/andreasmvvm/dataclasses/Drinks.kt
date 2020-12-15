package com.poema.andreasmvvm.dataclasses
import com.google.gson.annotations.SerializedName


data class Drinks(
    val drinks: List<Drink>
)

data class Drink(
    val dateModified: String? = null,
    val idDrink: String? = null,
    val strAlcoholic: String? = null,
    val strCategory: String? = null,
    val strCreativeCommonsConfirmed: String? = null,
    val strDrink: String? = null,
    val strDrinkAlternate: Any? = null,
    val strDrinkDE: Any? = null,
    val strDrinkES: Any? = null,
    val strDrinkFR: Any? = null,
    val strDrinkThumb: String? = null,
    @SerializedName("strDrinkZH-HANS")
    val strDrinkZHHANS: Any? = null,
    @SerializedName("strDrinkZH-HANT")
    val strDrinkZHHANT: Any? = null,
    val strGlass: String? = null,
    val strIBA: Any? = null,
    val strImageAttribution: Any? = null,
    val strImageSource: Any? = null,
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
    val strInstructionsDE: String? = null,
    val strInstructionsES: Any? = null,
    val strInstructionsFR: Any? = null,
    @SerializedName("strInstructionsZH-HANS")
    val strInstructionsZHHANS: Any? = null,
    @SerializedName("strInstructionsZH-HANT")
    val strInstructionsZHHANT: Any? = null,
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
    val strTags: Any? = null,
    val strVideo: Any? = null
)
