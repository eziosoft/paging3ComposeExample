package com.eziosoft.parisinnumbers.presentation.ui.listScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.eziosoft.parisinnumbers.data.remote.models.records.RecordX
import org.koin.androidx.compose.getViewModel

@Composable
fun ListScreen() {
    val viewModel = getViewModel<ListScreenViewModel>()
    val movies = viewModel.getMovies().collectAsLazyPagingItems()
    val listState: LazyListState = rememberLazyListState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(), state = listState
        ) {
            items(movies) { item ->
                item?.record?.let { record ->
                    ListItem(viewModel, record)
                }
            }
        }
    }
}

@Composable
private fun ListItem(
    viewModel: ListScreenViewModel,
    record: RecordX
) {
    Card(modifier = Modifier
        .clickable {
            viewModel.navigateToDetails(recordId = record.id)
        }
        .fillMaxWidth()
        .padding(4.dp), elevation = 5.dp) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = record.fields.nom_tournage, fontWeight = FontWeight.Bold)
            Text(text = record.fields.annee_tournage)
        }
    }
}
