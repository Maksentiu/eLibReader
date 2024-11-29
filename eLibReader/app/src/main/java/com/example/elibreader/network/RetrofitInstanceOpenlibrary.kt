package com.example.elibreader.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstanceOpenlibrary {
    private const val BASE_URL = "https://openlibrary.org/"


    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(OpenLibraryApi::class.java)
}

