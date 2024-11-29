package com.example.elibreader.screens


import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.elibreader.R
import com.example.elibreader.Routes
import com.example.elibreader.adapters.BookItem
import com.example.elibreader.models.BookViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(navController: NavHostController, viewModel: BookViewModel) {

    var query by rememberSaveable { mutableStateOf("") }
    val state = viewModel.stateOfDetails.collectAsState().value
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),

        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = Routes.searchScreen,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF00E676),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        bottomBar = {
            BottomAppBar(modifier = Modifier.height(70.dp)) {
                Row(modifier = Modifier.fillMaxSize()) {
                    Spacer(modifier = Modifier.weight(0.5f))

                    IconButton(onClick = { navController.navigate(Routes.settingsScreen) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.settings), // Используйте ресурс иконки
                            contentDescription = "Settings",
                            tint = Color.Unspecified // Используйте цвет по умолчанию или задайте свой
                        )
                    }
                    Spacer(modifier = Modifier.weight(0.75f))
                    IconButton(onClick = { navController.navigate(Routes.favoritesScreen) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.favorites), // Используйте ресурс иконки
                            contentDescription = "Favorites",
                            tint = Color.Unspecified // Используйте цвет по умолчанию или задайте свой
                        )
                    }
                    Spacer(modifier = Modifier.weight(0.75f))
                    IconButton(onClick = { navController.navigate(Routes.searchScreen) }) {
                        Icon(
                            imageVector = Icons.Filled.Search, // Используйте ресурс иконки
                            contentDescription = "Favorites",
                            tint = Color.Unspecified // Используйте цвет по умолчанию или задайте свой
                        )
                    }
                    Spacer(modifier = Modifier.weight(0.75f))
                    IconButton(onClick = { showDialog = true}) {
                        Icon(
                            painter = painterResource(id = R.drawable.exit), // Используйте ресурс иконки
                            contentDescription = "Exit",
                            tint = Color.Unspecified // Используйте цвет по умолчанию или задайте свой
                        )
                    }
                    Spacer(modifier = Modifier.weight(0.5f))
                }
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    Modifier

                        .verticalScroll(rememberScrollState())
                        .padding(paddingValues)
                )
                {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .padding(16.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        value = query,
                        onValueChange = { query = it },
                        label = {
                            Text(
                                text = "Search for books",
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF00E676)
                            )
                        },
                        colors = TextFieldDefaults.colors(Color(0xFF00E676))
                    )
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        onClick = {
                            viewModel.fetchSearchResults(query, viewModel)
                            Log.d("BookViewModel", "Search target")
                        },
                        colors = ButtonDefaults.buttonColors(Color(0xFF00E676))
                    ) {
                        Text(
                            text = "Search",
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                    }
                }
                LazyColumn(modifier = Modifier.height(600.dp)) {

                    items(state) {book ->
                        BookItem(
                            book,
                            onClick = {
                                viewModel.setSelectedBook(book)
                                viewModel.setPrevScreen(Routes.searchScreen)
                                navController.navigate(Routes.bookDetailScreen)
                            },
                            viewModel
                        )
                    }
                }
            }
        }
    )
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Подтверждение выхода") },
            text = { Text("Вы уверены, что хотите выйти из приложения?") },
            confirmButton = {
                TextButton(onClick = {
                    // Закрываем приложение
                    (context as? Activity)?.finishAffinity()
                }) {
                    Text("Да")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Нет")
                }
            }
        )
    }
}