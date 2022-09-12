package com.eziosoft.parisinnumbers.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.startActivity
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun DetailsScreen(movieId: String?) {
    val viewModel = getViewModel<DetailsScreenViewModel> { parametersOf(movieId) }
    val screenState = viewModel.screenStateFlow.collectAsState()
    val context = LocalContext.current

    Content(screenState.value, context)
}

@Composable
private fun Content(
    screenState: ScreenState,
    context: Context?
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Title: " + screenState.movieTitle)
        Text("Address: " + screenState.address)
        Text("Year: " + screenState.year)
        Text("Start date: " + screenState.startDate)
        Text("EndDate: " + screenState.endDate)
        Text("Producer: " + screenState.producer)
        Text("Realisation: " + screenState.realisation)
        Text("Type: " + screenState.type)

        Button(onClick = {
            openInGoogleMaps(
                context!!,
                screenState.lat,
                screenState.lon
            )
        }) {
            Text(text = "Open in Google Maps")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Content(screenState = ScreenState(), context = null)
}

private fun openInGoogleMaps(context: Context, lat: Double, lon: Double) {
    val url = "http://maps.google.com/maps?q=$lat,$lon"
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    startActivity(context, intent, null)
}
