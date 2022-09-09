package com.eziosoft.parisinnumbers.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eziosoft.parisinnumbers.data.remote.MoviesRepository
import com.eziosoft.parisinnumbers.data.remote.models.singleRecord.SingleRecord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// val adresse_lieu: String,
// val annee_tournage: String,
// val ardt_lieu: String,
// val coord_x: Double,
// val coord_y: Double,
// val date_debut: String,
// val date_fin: String,
// val geo_point_2d: GeoPoint2d,
// val geo_shape: GeoShape,
// val id_lieu: String,
// val nom_producteur: String,
// val nom_realisateur: String,
// val nom_tournage: String,
// val type_tournage: String

data class ScreenState(
    val movieTitle: String = "",
    val address: String = "",
    val year: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val producer: String = "",
    val realisation: String = "",
    val type: String = ""
)

fun SingleRecord.toScreenState() =
    with(record.fields) {
        ScreenState(
            movieTitle = nom_tournage,
            address = adresse_lieu,
            year = annee_tournage,
            startDate = date_debut,
            endDate = date_fin,
            producer = nom_producteur,
            realisation = nom_realisateur,
            type = type_tournage
        )
    }

class DetailsScreenViewModel(
    movieId: String,
    private val repository: MoviesRepository
) : ViewModel() {
    private val _contentFlow = MutableStateFlow(ScreenState())
    val screenStateFlow = _contentFlow.asStateFlow()

    init {
        getMovie(movieId)
        Log.d("nnnn", "view model init: ")
    }

    private fun getMovie(id: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.getMovie(id).onSuccess { record ->
            Log.d("aaa", "getMovie: $record")
            record?.let {
                _contentFlow.emit(it.toScreenState())
            }
        }
            .onFailure {
                Log.d("aaa", "getMovie: isFailure ${it.message}")
            }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("nnnn", "onCleared: ")
    }
}
