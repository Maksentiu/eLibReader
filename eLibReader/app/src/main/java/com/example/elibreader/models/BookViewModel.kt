package com.example.elibreader.models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elibreader.Dto.BookDetailsDto
import com.example.elibreader.repositorys.BookRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class BookViewModel : ViewModel() {

    private val favoriteItemDatabase = FirebaseDatabase.getInstance()
    private val ref = favoriteItemDatabase.getReference()
    private val repository = BookRepository()

    private val favoritesList = mutableListOf<BookDetailsDto>()
    var errorMessage: String? = null
    var stateOfDetails = MutableStateFlow(listOf<BookDetailsDto>())
    var prevScreen = MutableStateFlow(String())
    var UID = MutableStateFlow(String())

    // Создаем MutableStateFlow для хранения состояния выбранной книги
    private val _selectedBook = MutableStateFlow<BookDetailsDto?>(null)
    val selectedBook: StateFlow<BookDetailsDto?> get() = _selectedBook

    var favorites = MutableStateFlow(listOf<BookDetailsDto>())

    fun fetchSearchResults(query: String, viewModel: BookViewModel) {
        viewModelScope.launch {

            val list = repository.searchBooks(query)
            var detailsList: List<BookDetailsDto> = emptyList()
            var bookDetailsDto: BookDetailsDto =
                BookDetailsDto()

            try {

                Log.d("BookViewModel", list.toString())
                for (item in list) {
                    if (item != null) {
                        bookDetailsDto = repository.getBookDetails(item)
                    }
                    Log.d("BookViewModel", bookDetailsDto.title)
                    detailsList = detailsList + bookDetailsDto
                }
                Log.d("DETAILS", detailsList.get(0).title)

                stateOfDetails.emit(detailsList)
                errorMessage = null

            } catch (e: Exception) {
                errorMessage = e.message

                Log.d("BookViewModel", "khjh")
            }
        }
    }

    suspend fun loadFavoritesFromDatabase() {
        val userRef = ref.child(UID.value)
        try {
            favoritesList.clear()
            val snapshot = userRef.get().await()
            if (snapshot.exists()) {
                favoritesList.addAll(snapshot.children.mapNotNull { it.getValue<BookDetailsDto>() })
                favorites.value = favoritesList
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun addBookToFavorites(book: BookDetailsDto) {
        if (favoritesList.none { it.key == book.key }) { // Предполагается, что 'key' уникален для каждой книги
            favoritesList.add(book) // Добавляем книгу в список избранных
            favorites.value = favoritesList // Обновляем состояние избранных книг
            ref.child(UID.value).removeValue()
            ref.child(UID.value).setValue(favoritesList)// Сохраняем обновленный список в базе данных
        }
    }


    // Метод для установки выбранной книги
    fun setSelectedBook(book: BookDetailsDto) {
        _selectedBook.value = book
    }

    fun setUID(uid: String) {
        UID.value = uid
    }

    fun setPrevScreen(prev: String) {
        prevScreen.value = prev
    }

    fun getImageUrl(coverId: Int): String {
        val baseUrl = "https://covers.openlibrary.org/b/id/"
        return "$baseUrl$coverId-L.jpg" // Используем "-L" для большого формата изображения
    }

    fun removeFavorite(book: BookDetailsDto) {
        favorites.value = favorites.value.filter { it.key != book.key }
    }
}