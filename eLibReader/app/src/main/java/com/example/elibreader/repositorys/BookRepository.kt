package com.example.elibreader.repositorys

import com.example.elibreader.Dto.BookDetailsDto
import com.example.elibreader.network.RetrofitInstanceOpenlibrary

class BookRepository {

    suspend fun getBookDetails(id: String): BookDetailsDto {
        return RetrofitInstanceOpenlibrary.apiService.getBookDetails(id)
    }

    suspend fun searchBooks(query: String): List<String> {
        return RetrofitInstanceOpenlibrary.apiService.searchBooks(query).docs
            .take(10)
            .map {
                it.cover_edition_key
            }
    }

}