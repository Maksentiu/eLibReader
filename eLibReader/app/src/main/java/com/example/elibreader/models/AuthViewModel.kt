package com.example.elibreader.models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.elibreader.Routes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    var user: FirebaseUser? = null
    var errorMessage: String? = null

    fun login(email: String, password: String, navController: NavController, viewModel: BookViewModel) {
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                user = auth.currentUser

                viewModel.setUID(auth.uid.toString())
                viewModel.loadFavoritesFromDatabase()
                Log.d("BookViewModel", viewModel.UID.value)
                errorMessage = null

                if (user != null) {

                    navController.navigate(Routes.searchScreen)
                } else {
                    errorMessage = "user is null"
                }
            } catch (e: FirebaseAuthInvalidUserException) {
                errorMessage = "No user found with this email."
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                errorMessage = "Invalid password. Please try again."
            } catch (e: Exception) {
                errorMessage = e.message ?: "Login failed. Please try again."
            }
        }
    }

    fun register(email: String, password: String, navController: NavController, viewModel: BookViewModel) {
        viewModelScope.launch {
            try {
                // Проверка на валидность электронной почты и пароля
                if (email.isBlank() || password.length < 6) {
                    errorMessage = "Email must not be empty and password must be at least 6 characters."
                    return@launch
                }

                // Попытка регистрации пользователя
                auth.createUserWithEmailAndPassword(email, password).await()
                user = auth.currentUser
                Log.d("BookViewModel", viewModel.UID.value)
                viewModel.setUID(auth.uid.toString())
                viewModel.loadFavoritesFromDatabase()
                errorMessage = null

                // Переход на следующий экран после успешной регистрации
                navController.navigate(Routes.searchScreen)
            } catch (e: FirebaseAuthUserCollisionException) {
                // Ошибка, если пользователь уже существует
                errorMessage = "This email address is already in use."
            } catch (e: FirebaseAuthWeakPasswordException) {
                // Ошибка, если пароль слишком слабый
                errorMessage = "The password is too weak. Please choose a stronger password."
            } catch (e: Exception) {
                // Общая ошибка
                errorMessage = e.message ?: "Registration failed. Please try again."
            }
        }
    }
}