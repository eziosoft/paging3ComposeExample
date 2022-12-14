package com.eziosoft.parisinnumbers.presentation.ui.movieDetailsBottomSheet

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.eziosoft.parisinnumbers.R
import com.eziosoft.parisinnumbers.domain.Movie
import org.koin.androidx.compose.getViewModel

@Composable
fun MovieDetailsBottomSheet() {
    val viewModel = getViewModel<MovieDetailsBottomSheetViewModel>()
    val state = viewModel.state
    val movie = state.movie

    LaunchedEffect(key1 = viewModel.actionDispatcher.sharedParameters.selectedMovieId.value) {
        viewModel.getMovieById()
    }
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .padding(4.dp)
                .height(2.dp)
                .width(50.dp)
                .background(Color.White)
        )
        Content(movie)
    }
}

@Composable
private fun Content(movie: Movie) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = movie.title,
            style = MaterialTheme.typography.h6,
            color = Color.White,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = movie.year,
            style = MaterialTheme.typography.body1,
            color = Color.LightGray,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Log.d("aaa", "Content: ${movie.posterUrl}")
            AnimatedVisibility(movie.posterUrl.isNotEmpty()) {
                AsyncImage(
                    modifier = Modifier
                        .height(200.dp),
                    model = movie.getFullPosterUrl(),
                    contentDescription = movie.title,
                    fallback = painterResource(id = R.drawable.ic_baseline_local_movies_24),
                    placeholder = painterResource(id = R.drawable.ic_baseline_local_movies_24),
                    error = painterResource(id = R.drawable.ic_baseline_local_movies_24)
                )
            }

            Column(
                modifier = Modifier
                    .padding(4.dp)
            ) {
                Text(
                    text = "Address: ${movie.address}",
                    style = MaterialTheme.typography.body1,
                    color = Color.White
                )
                Text(
                    text = "Time: ${movie.startDate} - ${movie.endDate}",
                    style = MaterialTheme.typography.body1,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Type: ${movie.type}",
                    style = MaterialTheme.typography.body1,
                    color = Color.LightGray
                )
                Text(
                    text = "Production: ${movie.producer}",
                    style = MaterialTheme.typography.body1,
                    color = Color.LightGray
                )
                Text(
                    text = "Realisation: ${movie.realisation}",
                    style = MaterialTheme.typography.body1,
                    color = Color.LightGray
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        AnimatedVisibility(visible = movie.description.isNotEmpty()) {
            Text(
                text = movie.description,
                style = MaterialTheme.typography.body1,
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF555555)
@Composable
private fun Preview() {
    val sampleMovie = Movie(
        "id",
        "address",
        "2021",
        "ardt",
        0.0,
        0.0,
        "startDate",
        "endDate",
        "placeId",
        "producer",
        "realisation",
        "Emily in Paris",
        "type",
        "this is the description",
        "posterURL"
    )

    Content(movie = sampleMovie)
}
