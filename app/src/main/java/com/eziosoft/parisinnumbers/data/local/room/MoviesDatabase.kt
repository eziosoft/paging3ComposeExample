package com.eziosoft.parisinnumbers.data.local.room

import androidx.room.RoomDatabase
import com.eziosoft.parisinnumbers.data.local.room.movieDetails.MovieDetailsDao
import com.eziosoft.parisinnumbers.data.local.room.movieDetails.RoomMovieDetails
import com.eziosoft.parisinnumbers.data.local.room.movies.MovieDao
import com.eziosoft.parisinnumbers.data.local.room.movies.RoomMovie

@androidx.room.Database(entities = [RoomMovie::class, RoomMovieDetails::class], version = 1)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    abstract fun roomMovieDetailsDao(): MovieDetailsDao
}
