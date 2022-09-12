package com.eziosoft.parisinnumbers.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import org.koin.androidx.compose.getViewModel

@Composable
fun ListScreen(onItemClick: (movieTitle: String) -> Unit) {
    val viewModel = getViewModel<ListScreenViewModel>()
    val movies = viewModel.getMovies().collectAsLazyPagingItems()
    val listState: LazyListState = rememberLazyListState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState
        ) {
            items(movies) { item ->
                item?.record?.let { record ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onItemClick(record.id)
                            }
                    ) {
                        Text(text = record.fields.nom_tournage)
                    }
                }
            }
        }
    }
}
