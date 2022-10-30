package com.eziosoft.parisinnumbers.presentation.ui.listScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.eziosoft.parisinnumbers.R
import com.eziosoft.parisinnumbers.domain.Movie

@Composable
fun ListItem(
    movie: Movie,
    onClick: (movieId: String) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(Color(0xFF2d2d30))
            .aspectRatio(1f)
            .clickable {
                onClick(movie.id)
            },
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            error = painterResource(id = R.drawable.ic_baseline_local_movies_24),
            contentScale = ContentScale.Crop,
            model = movie.getFullPosterUrl(),
            contentDescription = movie.title,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .padding(8.dp)
        ) {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.h6,
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = movie.year,
                style = MaterialTheme.typography.body1,
                color = Color.White,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = movie.address,
                style = MaterialTheme.typography.body2,
                color = Color.LightGray,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun preview() {
    ListItem(
        movie = Movie(
            id = "asda",
            address = "asdasasdd",
            year = "1234",
            district = "75000",
            startDate = "start date",
            endDate = "end date",
            producer = "producer",
            realisation = "realisation",
            title = "title",
            description = "this is the description"
        ),
        onClick = {}
    )
}
