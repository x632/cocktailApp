package com.poema.andreasmvvm.api

import com.poema.andreasmvvm.dataclasses.Drinks

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
        //api/json/v1/1/search.php?f=c
        @GET("api/json/v1/1/search.php")
        fun getDrinksByLetter(@Query("f") letter : String) : Call<Drinks>
}
