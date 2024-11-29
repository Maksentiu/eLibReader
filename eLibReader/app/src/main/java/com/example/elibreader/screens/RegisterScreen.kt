package com.example.elibreader.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.elibreader.Routes
import com.example.elibreader.models.AuthViewModel
import com.example.elibreader.models.BookViewModel

@Composable
fun RegisterScreen(navController: NavController, viewBookModel: BookViewModel) {
    val viewModel: AuthViewModel = viewModel()
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp)), // Округленные углы
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp)),
        )

        viewModel.errorMessage?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error
            )
        }
        Button(
            onClick = {
                viewModel.register(email, password, navController, viewBookModel)
            },
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
        )
        {
            Text("Registration")
        }
        Button(
            onClick = { navController.navigate(Routes.loginScreen) },
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            Text("Log in")
        }


    }
}
