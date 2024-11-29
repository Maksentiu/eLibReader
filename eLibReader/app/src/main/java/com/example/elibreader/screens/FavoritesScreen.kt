package com.example.elibreader.screens

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.elibreader.R
import com.example.elibreader.Routes
import com.example.elibreader.adapters.BookItem
import com.example.elibreader.models.BookViewModel
import androidx.compose.material.DismissValue as DismissValue1

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun FavoritesScreen(navController: NavController, viewModel: BookViewModel) {
    val favorites by viewModel.favorites.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier.fillMaxSize(),

        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Routes.searchScreen) }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                title = {
                    Text(text = Routes.favoritesScreen, color = Color.White)
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
                    IconButton(onClick = { showDialog = true }) {
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
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(
                    modifier = Modifier
                        .height(60.dp)
                )

                // Проверка на наличие избранных элементов
                if (favorites.isEmpty()) {
                    Text("No favorites yet.", style = MaterialTheme.typography.bodyMedium)
                } else {

                    LazyColumn(modifier = Modifier.height(600.dp)) {

                        items(favorites) { book ->
                            BookItem(
                                book,
                                onClick = {
                                    viewModel.setSelectedBook(book)
                                    viewModel.setPrevScreen(Routes.favoritesScreen)
                                    navController.navigate(Routes.bookDetailScreen)
                                },
                                viewModel
                            )
                        }
                    }

                    /*LazyColumn(modifier = Modifier.height(600.dp)) {
                        items(favorites) { book ->
                            SwipeToDismiss(
                                state = rememberSwipeToDismissBoxState(
                                    confirmValueChange = { dismissValue ->
                                        if (dismissValue == DismissValue.DismissedToStart) {
                                            // Удаляем книгу из избранного
                                            viewModel.removeFavorite(book)
                                            true // Подтверждаем завершение свайпа
                                        } else {
                                            false // Не подтверждаем свайп
                                        }
                                    }
                                ),
                                background = {
                                    // Фон при свайпе
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(horizontal = 16.dp),
                                        contentAlignment = Alignment.CenterEnd
                                    ) {
                                        Icon(
                                            Icons.Filled.Delete, // Иконка удаления
                                            contentDescription = "Delete",
                                            tint = Color.White,
                                            modifier = Modifier.padding(end = 16.dp)
                                        )
                                    }
                                },
                                dismissContent = {
                                    // Ваш элемент списка
                                    BookItem(
                                        book,
                                        onClick = {
                                            viewModel.setSelectedBook(book)
                                            viewModel.setPrevScreen(Routes.favoritesScreen)
                                            navController.navigate(Routes.bookDetailScreen)
                                        },
                                        viewModel
                                    )
                                }
                            )
                        }
                    }*/

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