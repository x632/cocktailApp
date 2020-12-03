package com.poema.andreasmvvm.dataclasses
import com.google.gson.annotations.SerializedName


data class Drinks(
    @SerializedName("drinks")
    val drinks: List<Drink>
)

data class Drink(
    @SerializedName("dateModified")
    val dateModified: String,
    @SerializedName("idDrink")
    val idDrink: String,
    @SerializedName("strAlcoholic")
    val strAlcoholic: String,
    @SerializedName("strCategory")
    val strCategory: String,
    @SerializedName("strCreativeCommonsConfirmed")
    val strCreativeCommonsConfirmed: String,
    @SerializedName("strDrink")
    val strDrink: String,
    @SerializedName("strDrinkAlternate")
    val strDrinkAlternate: Any,
    @SerializedName("strDrinkDE")
    val strDrinkDE: Any,
    @SerializedName("strDrinkES")
    val strDrinkES: Any,
    @SerializedName("strDrinkFR")
    val strDrinkFR: Any,
    @SerializedName("strDrinkThumb")
    val strDrinkThumb: String,
    @SerializedName("strDrinkZH-HANS")
    val strDrinkZHHANS: Any,
    @SerializedName("strDrinkZH-HANT")
    val strDrinkZHHANT: Any,
    @SerializedName("strGlass")
    val strGlass: String,
    @SerializedName("strIBA")
    val strIBA: Any,
    @SerializedName("strImageAttribution")
    val strImageAttribution: Any,
    @SerializedName("strImageSource")
    val strImageSource: Any,
    @SerializedName("strIngredient1")
    val strIngredient1: String,
    @SerializedName("strIngredient10")
    val strIngredient10: Any,
    @SerializedName("strIngredient11")
    val strIngredient11: Any,
    @SerializedName("strIngredient12")
    val strIngredient12: Any,
    @SerializedName("strIngredient13")
    val strIngredient13: Any,
    @SerializedName("strIngredient14")
    val strIngredient14: Any,
    @SerializedName("strIngredient15")
    val strIngredient15: Any,
    @SerializedName("strIngredient2")
    val strIngredient2: String,
    @SerializedName("strIngredient3")
    val strIngredient3: String,
    @SerializedName("strIngredient4")
    val strIngredient4: String,
    @SerializedName("strIngredient5")
    val strIngredient5: Any,
    @SerializedName("strIngredient6")
    val strIngredient6: Any,
    @SerializedName("strIngredient7")
    val strIngredient7: Any,
    @SerializedName("strIngredient8")
    val strIngredient8: Any,
    @SerializedName("strIngredient9")
    val strIngredient9: Any,
    @SerializedName("strInstructions")
    val strInstructions: String,
    @SerializedName("strInstructionsDE")
    val strInstructionsDE: String,
    @SerializedName("strInstructionsES")
    val strInstructionsES: Any,
    @SerializedName("strInstructionsFR")
    val strInstructionsFR: Any,
    @SerializedName("strInstructionsZH-HANS")
    val strInstructionsZHHANS: Any,
    @SerializedName("strInstructionsZH-HANT")
    val strInstructionsZHHANT: Any,
    @SerializedName("strMeasure1")
    val strMeasure1: String,
    @SerializedName("strMeasure10")
    val strMeasure10: Any,
    @SerializedName("strMeasure11")
    val strMeasure11: Any,
    @SerializedName("strMeasure12")
    val strMeasure12: Any,
    @SerializedName("strMeasure13")
    val strMeasure13: Any,
    @SerializedName("strMeasure14")
    val strMeasure14: Any,
    @SerializedName("strMeasure15")
    val strMeasure15: Any,
    @SerializedName("strMeasure2")
    val strMeasure2: String,
    @SerializedName("strMeasure3")
    val strMeasure3: String,
    @SerializedName("strMeasure4")
    val strMeasure4: String,
    @SerializedName("strMeasure5")
    val strMeasure5: Any,
    @SerializedName("strMeasure6")
    val strMeasure6: Any,
    @SerializedName("strMeasure7")
    val strMeasure7: Any,
    @SerializedName("strMeasure8")
    val strMeasure8: Any,
    @SerializedName("strMeasure9")
    val strMeasure9: Any,
    @SerializedName("strTags")
    val strTags: Any,
    @SerializedName("strVideo")
    val strVideo: Any
)