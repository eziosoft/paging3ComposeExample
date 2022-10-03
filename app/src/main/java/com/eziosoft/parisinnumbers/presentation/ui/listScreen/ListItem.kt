package com.eziosoft.parisinnumbers.presentation.ui.listScreen

import android.graphics.ColorMatrixColorFilter
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.eziosoft.parisinnumbers.R


private val COLOR_MATRIX = floatArrayOf(
    85f, 85f, 85f, 0f, -128 * 255f,
    85f, 85f, 85f, 0f, -128 * 255f,
    85f, 85f, 85f, 0f, -128 * 255f,
    0f, 0f, 0f, 0.2f, 0f
)

@Composable
fun ListItem(
    viewModel: ListScreenViewModel,
    movie: MovieTitle
) {
    var posterUrl by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = true) {
        viewModel.searchInfoAboutMovie(movie.title) {
            posterUrl = it
        }
        Log.d("aaaa", "ListItem: LaunchEffect")
    }

    Box(
        modifier = Modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(Color(0xFF2d2d30))
            .aspectRatio(1f)
            .clickable {
                viewModel.navigateToDetails(movieTitle = movie.title)
            },
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            error = painterResource(id = R.drawable.ic_baseline_local_movies_24),
            contentScale = ContentScale.Crop,
//            colorFilter = ColorMatrixColorFilter(COLOR_MATRIX).asComposeColorFilter(),
            model = posterUrl,
            contentDescription = movie.title,
            modifier = Modifier.fillMaxSize(),

            )

        Text(
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.4f))
                .padding(6.dp),
            text = movie.title,
            style = MaterialTheme.typography.h6,
            color = Color.White,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis


        )


    }
}

