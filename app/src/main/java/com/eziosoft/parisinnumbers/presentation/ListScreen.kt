package com.eziosoft.parisinnumbers.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import org.koin.androidx.compose.getViewModel

@Composable
fun ListScreen() {
    val viewModel = getViewModel<ListScreenViewModel>()

    val movies = viewModel.getMovies().collectAsLazyPagingItems()

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