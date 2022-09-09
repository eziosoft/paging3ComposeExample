package com.eziosoft.parisinnumbers

import com.eziosoft.parisinnumbers.presentation.DetailsScreenViewModel
import com.eziosoft.parisinnumbers.presentation.ListScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        ListScreenViewModel(repository = get())
    }

    viewModel { (movieId: String) ->
        DetailsScreenViewModel(movieId = movieId, repository = get())
    }
}
