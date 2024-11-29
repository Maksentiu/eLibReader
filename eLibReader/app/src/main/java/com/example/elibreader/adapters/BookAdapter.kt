package com.example.elibreader.adapters

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.elibreader.Dto.BookDetailsDto
import com.example.elibreader.R
import com.example.elibreader.models.BookViewModel

@Composable
fun BookItem(book: BookDetailsDto, onClick: () -> Unit, viewModel: BookViewModel) {
    Box(
        modifier = Modifier
            .padding(30.dp)
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .height(100.dp)
            .background(Color.Gray)
            .border(5.dp, Color(0xFF00E676))
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center,
    ) {
        Row {
            if (book.covers.isNotEmpty()) {
                val coverId = book.covers[0] // Получаем первый ID обложки
                val imageUrl = viewModel.getImageUrl(coverId)
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Book Cover",
                    modifier = Modifier
                        .size(100.dp) // Установите нужный размер
                        .padding(16.dp),
                    error = painterResource(id = R.drawable.cover_not_found), // Заглушка на случай ошибки
                    placeholder = painterResource(id = R.drawable.loading) // Заглушка во время загрузки
                )
            } else {
                Text("No cover available")
            }
            Text(
                text = book.title, fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}


