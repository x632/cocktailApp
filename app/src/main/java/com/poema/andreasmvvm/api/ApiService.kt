package com.poema.andreasmvvm.api

import com.poema.andreasmvvm.dataclasses.Drinks

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

        @GET("api/json/v1/1/search.php")
        fun getDrinksByLetter(@Query("f") letter : String) : Call<Drinks>

        @GET("api/json/v1/1/search.php")
        fun getDrinksByName(@Query("s") letter : String) : Call<Drinks>
}
