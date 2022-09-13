package com.eziosoft.parisinnumbers.presentation.ui.detailsScreen

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import org.koin.androidx.compose.getViewModel

@Composable
fun DetailsScreen() {
    val viewModel = getViewModel<DetailsScreenViewModel>()
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
            viewModel.hideBottomSheet()
            viewModel.navigateToList()
        }) {
            Icon(imageVector = Icons.Default.Home, contentDescription = "Back")
        }
        Content(screenState)

        Button(onClick = {
            openInGoogleMaps(
                context!!,
                screenState.lat,
                screenState.lon
            )
        }) {
            Text(text = "Open in Google Maps")
        }

        IconButton(onClick = {
            viewModel.showBottomSheet() {
                Box() {
                    Content(screenState = screenState)
                }
            }
        }) {
            Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "Up")
        }

        IconButton(onClick = {
            viewModel.hideBottomSheet()
        }) {
            Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "Down")
        }

        IconButton(onClick = { viewModel.showSnackbar("The title is ${screenState.movieTitle}") }) {
            Icon(imageVector = Icons.Default.Info, contentDescription = "Info")
        }
    }
}

@Composable
private fun Content(screenState: ScreenState) {
    Text("Title: " + screenState.movieTitle)
    Text("Address: " + screenState.address)
    Text("Year: " + screenState.year)
    Text("Start date: " + screenState.startDate)
    Text("EndDate: " + screenState.endDate)
    Text("Producer: " + screenState.producer)
    Text("Realisation: " + screenState.realisation)
    Text("Type: " + screenState.type)
}

private fun openInGoogleMaps(context: Context, lat: Double, lon: Double) {
    val url = "http://maps.google.com/maps?q=$lat,$lon"
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    startActivity(context, intent, null)
}
