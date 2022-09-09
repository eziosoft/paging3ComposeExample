package com.eziosoft.parisinnumbers.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun DetailsScreen(movieId: String?) {
    val viewModel = getViewModel<DetailsScreenViewModel> { parametersOf(movieId) }
    val screenState = viewModel.screenStateFlow.collectAsState()

    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Column() {
            Text("Title: " + screenState.value.movieTitle)
            Text("Address: " + screenState.value.address)
            Text("Year: " + screenState.value.year)
            Text("Start date: " + screenState.value.startDate)
            Text("EndDate: " + screenState.value.endDate)
            Text("Producer: " + screenState.value.producer)
            Text("Realisation: " + screenState.value.realisation)
            Text("Type: " + screenState.value.type)

            Button(onClick = {
                openInGoogleMaps(
                    context,
                    screenState.value.lat,
                    screenState.value.lon
                )
            }) {
                Text(text = "Open in Google Maps")
            }
        }
    }
}

fun openInGoogleMaps(context: Context, lat: Double, lon: Double) {
    val url = "http://maps.google.com/maps?q=$lat,$lon"
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    startActivity(context, intent, null)
}
