package com.eziosoft.parisinnumbers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.eziosoft.MainViewModel
import com.eziosoft.parisinnumbers.ui.theme.ParisInNumbersTheme
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    val viewmodel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            viewmodel.getMovies().toList()
        }

        setContent {
            ParisInNumbersTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    val movies = viewmodel.getMovies().collectAsLazyPagingItems()

//                    Log.d("vvvv", "onCreate:   ${movies.itemCount}")
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
            }
        }
    }
}
