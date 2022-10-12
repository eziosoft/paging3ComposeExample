package com.eziosoft.parisinnumbers

import com.eziosoft.parisinnumbers.presentation.ProjectDispatchers
import com.eziosoft.parisinnumbers.presentation.ui.listScreen.ListScreenViewModel
import com.eziosoft.parisinnumbers.presentation.ui.mapScreen.MapScreenViewModel
import com.eziosoft.parisinnumbers.presentation.ui.movieDetailsBottomSheet.MovieDetailsBottomSheetViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        ListScreenViewModel(get(), get(), get(), get())
    }

    viewModel {
        MapScreenViewModel(get(), get(), get())
    }

    single {
        ProjectDispatchers()
    }

    viewModel {
        MovieDetailsBottomSheetViewModel(get(), get(), get(), get())
    }
}
