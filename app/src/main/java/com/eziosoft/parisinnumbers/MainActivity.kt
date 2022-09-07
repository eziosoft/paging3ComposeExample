package com.eziosoft.parisinnumbers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.eziosoft.MainViewModel
import com.eziosoft.parisinnumbers.ui.theme.ParisInNumbersTheme
import org.koin.androidx.compose.getViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ParisInNumbersTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val mainViewModel = getViewModel<MainViewModel>()

    val movies = mainViewModel.getMovies().collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(movies) { item ->
            item?.record?.fields?.nom_tournage?.let {
                Text(text = it)
            }
        }
    }
}
