package com.example.elibreader.Dto

import com.google.gson.annotations.SerializedName

data class BookSearchResponse(
    @SerializedName("docs")
    val docs:List<BookDto>,
)