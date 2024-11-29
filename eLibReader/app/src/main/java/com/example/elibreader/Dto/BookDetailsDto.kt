package com.example.elibreader.Dto

data class BookDetailsDto(
    val number_of_pages: String = "",
    val publish_date: String = "",
    val publishers: List<String> = emptyList(),
    val title: String = "",
    val key: String = "",
    val covers: List<Int> = emptyList()
)
