package com.eziosoft.parisinnumbers

import com.eziosoft.parisinnumbers.presentation.navigation.ActionDispatcher
import com.eziosoft.parisinnumbers.presentation.ui.detailsScreen.DetailsScreenViewModel
import com.eziosoft.parisinnumbers.presentation.ui.listScreen.ListScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        ListScreenViewModel(repository = get(), get())
    }

    viewModel { (movieId: String) ->
        DetailsScreenViewModel(movieId = movieId, repository = get(), actionDispatcher = get())
    }

    single { ActionDispatcher() }
}
