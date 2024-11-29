package com.example.elibreader.ui.auth

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.elibreader.models.AuthViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.elibreader.Routes
import com.example.elibreader.models.BookViewModel

@Composable
fun LoginScreen(navController: NavController, viewBookModel: BookViewModel) {
    val viewModel: AuthViewModel = viewModel()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
        )

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
        )

        viewModel.errorMessage?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
        }

        Button(
            onClick = {
                viewModel.login(email, password, navController, viewBookModel)
            },
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            Text("Login")
        }

        Button(
            onClick = { navController.navigate(Routes.registerScreen) },
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            Text("Registration")
        }
    }
}