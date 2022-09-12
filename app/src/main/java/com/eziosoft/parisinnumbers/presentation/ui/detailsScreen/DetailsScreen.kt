package com.eziosoft.parisinnumbers.presentation.ui.detailsScreen

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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

    Content(screenState.value, context, viewModel)
}

@Composable
private fun Content(
    screenState: ScreenState,
    context: Context?,
    viewModel: DetailsScreenViewModel
) {
    Column(modifier = Modifier.fillMaxSize()) {
        IconButton(onClick = {
            viewModel.showBottomSheet(false)
            viewModel.navigateToList()
        }) {
            Icon(imageVector = Icons.Default.Home, contentDescription = "Back")
        }
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

        IconButton(onClick = { viewModel.showBottomSheet(true) }) {
            Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "Up")
        }

        IconButton(onClick = { viewModel.showBottomSheet(false) }) {
            Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "Down")
        }
    }
}

private fun openInGoogleMaps(context: Context, lat: Double, lon: Double) {
    val url = "http://maps.google.com/maps?q=$lat,$lon"
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    startActivity(context, intent, null)
}
