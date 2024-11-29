@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.elibreader.screens

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.elibreader.R
import com.example.elibreader.Routes
import com.example.elibreader.models.BookViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(navController: NavController, viewModel: BookViewModel) {


    val bookDetailsDto = viewModel.selectedBook.collectAsState().value
    val prevScreen = viewModel.prevScreen.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    if (bookDetailsDto == null) {
        // Выводим сообщение или возвращаем на предыдущий экран
        Text("No book details available.")
        return
    }

    Scaffold(modifier = Modifier.fillMaxSize(),

        topBar = {
            CenterAlignedTopAppBar(navigationIcon = {
                IconButton(onClick = {
                    if (prevScreen.value == Routes.searchScreen) {
                        navController.navigate(Routes.searchScreen)
                    } else if (prevScreen.value == Routes.favoritesScreen) {
                        navController.navigate(Routes.favoritesScreen)
                    }
                }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }, title = {
                Text(text = Routes.bookDetailScreen, color = Color.Black)
            }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
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
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(
                    modifier = Modifier
                        .padding(30.dp)
                        .height(60.dp)
                )
                Text(
                    modifier = Modifier.height(70.dp),
                    text = bookDetailsDto!!.title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineMedium
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxSize(0.5f),
                    horizontalArrangement = Arrangement.Start, // Выравнивание по левому краю
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val coverId = bookDetailsDto.covers[0] // Получаем первый ID обложки
                    val imageUrl = viewModel.getImageUrl(coverId)
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Book Cover",
                        modifier = Modifier
                            .weight(0.6f)
                            .size(250.dp),// Установите нужный размер
                        error = painterResource(id = R.drawable.cover_not_found), // Заглушка на случай ошибки
                        placeholder = painterResource(id = R.drawable.loading) // Заглушка во время загрузки
                    )
                    if (imageUrl.isNotEmpty()) {
                    } else {
                        Text("No cover available")
                    }

                    Column(
                        modifier = Modifier.weight(1f),// Позволяет Column занимать оставшееся пространство
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top,
                    ) {

                        // Заголовок книги


                        // Год публикации
                        bookDetailsDto.publish_date.let { year ->
                            Text(
                                text = "Published Year: $year", style = TextStyle(fontSize = 20.sp)

                            )
                        }

                        // Издатели
                        bookDetailsDto.publishers.let { publishers ->
                            Text(
                                text = "Publishers: ${publishers.joinToString(", ")}",
                                style = TextStyle(fontSize = 24.sp)
                            )
                        }

                        // Количество страниц
                        bookDetailsDto.number_of_pages.let { pages ->
                            Text(
                                text = "Number of Pages: $pages",
                                style = TextStyle(fontSize = 24.sp)
                            )
                        }

                    }
                }
                Row() {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        onClick = {
                            viewModel.addBookToFavorites(bookDetailsDto)
                            Log.d("BookViewModel", "book saved")
                        },
                        colors = ButtonDefaults.buttonColors(Color(0xFF00E676))
                    ) {
                        Text(
                            text = "Save to favorites",
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
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
