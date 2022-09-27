package com.eziosoft.parisinnumbers.presentation.ui.listScreen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.eziosoft.parisinnumbers.domain.Movie
import org.koin.androidx.compose.getViewModel

@Composable
fun ListScreen(modifier: Modifier = Modifier.fillMaxSize()) {
    val viewModel = getViewModel<ListScreenViewModel>()
    val movies = viewModel.getMovies().collectAsLazyPagingItems()
    val listState: LazyListState = rememberLazyListState()

    Box(
        modifier = modifier
    ) {
        Column {
            Search(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                onSearch = {
                    viewModel.search(it)
                }
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 4.dp),
                state = listState
            ) {
                items(movies) { item ->
                    item?.let { ListItem(viewModel, it) }
                }
            }
        }
    }
}

@Composable
private fun ListItem(
    viewModel: ListScreenViewModel,
    record: Movie
) {
    var posterUrl by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = true) {
        viewModel.searchInfoAboutMovie(record.title) {
            posterUrl = it
        }
        Log.d("aaaa", "ListItem: LaunchEffect")
    }

    Card(
        modifier = Modifier
            .clickable {
                viewModel.navigateToDetails(recordId = record.id)
            }
            .fillMaxWidth()
            .padding(4.dp),
        elevation = 5.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = record.title, fontWeight = FontWeight.Bold)
                Text(text = "${record.startDate} - ${record.endDate}")
                Text(text = record.address)
            }
            AsyncImage(
                model = posterUrl,
                contentDescription = "",
                modifier = Modifier
                    .height(100.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Search(
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    var text by rememberSaveable() {
        mutableStateOf("")
    }
    Column(
        modifier = modifier
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                maxLines = 1,
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search"
                    )
                },
                label = { Text(text = "Search title") },
                onValueChange = {
                    text = it
                    onSearch(text)
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Default),
                keyboardActions = KeyboardActions(onDone = {
                    onSearch(text)
                    keyboardController?.hide()
                })
            )
        }
    }
}
