package com.eziosoft.parisinnumbers.data.local.room.movieDetails

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movieDetails: LocalMovieDetails)

    @Query("SELECT * FROM movieDetails WHERE title LIKE '%' || :title || '%'")
    fun get(title: String): List<LocalMovieDetails>
}
