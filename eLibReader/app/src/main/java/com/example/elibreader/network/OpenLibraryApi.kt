package com.example.elibreader.network

import com.example.elibreader.Dto.BookDetailsDto
import com.example.elibreader.Dto.BookDto
import com.example.elibreader.Dto.BookSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OpenLibraryApi {

    // Получение книги по её идентификатору
    @GET("books/{id}.json")
    suspend fun getBookDetails(@Path("id") id: String): BookDetailsDto

    // Поиск книг по множеству параметров
    @GET("search.json")
    suspend fun searchBooks(
        @Query("q") query: String
    ): BookSearchResponse



}