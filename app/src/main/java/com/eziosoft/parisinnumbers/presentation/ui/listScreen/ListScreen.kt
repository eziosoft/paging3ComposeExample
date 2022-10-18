package com.eziosoft.parisinnumbers.presentation.ui.listScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.eziosoft.parisinnumbers.R
import com.eziosoft.parisinnumbers.presentation.ui.movieDetailsBottomSheet.MovieDetailsBottomSheet
import org.koin.androidx.compose.getViewModel

@Composable
fun ListScreen(modifier: Modifier = Modifier) {
    val viewModel = getViewModel<ListScreenViewModel>()
    val state = viewModel.state
    val listState: LazyGridState = rememberLazyGridState()

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
            if (state.items.isNotEmpty() || state.isLoading) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier
                        .fillMaxSize(),
                    state = listState
                ) {
                    items(state.items.size) { i ->
                        if (i >= state.items.size - 1 &&
                            !state.endReached &&
                            !state.isLoading
                        ) {
                            viewModel.loadNextItems()
                        }
                        val item = state.items[i]
                        ListItem(item, onClick = {
                            viewModel.showMovieDetails(
                                id = it,
                                content = {
                                    MovieDetailsBottomSheet()
                                }
                            )
                        })
                    }
                    item {
                        if (state.isLoading) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                LinearProgressIndicator()
                            }
                        }
                    }
                }
            } else {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_browser_not_supported_24),
                            contentDescription = "empty list"

                        )
                        Text("Not found")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Search(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    var text by rememberSaveable {
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
