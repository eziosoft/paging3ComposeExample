package com.eziosoft.parisinnumbers.data.local.room.movieDetails

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movieDetails")
data class RoomMovieDetails(
    @PrimaryKey val title: String,
    val posterUrl: String,
    val description: String
)
