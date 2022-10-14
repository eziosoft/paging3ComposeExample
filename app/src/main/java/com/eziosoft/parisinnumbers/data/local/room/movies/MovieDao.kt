package com.eziosoft.parisinnumbers.data.local.room.movies

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(roomMovies: List<RoomMovie>)

    @Query("SELECT * FROM movies")
    fun getAll(): List<RoomMovie>

    @Query("SELECT * FROM movies WHERE id=:id")
    fun getMovie(id: String): List<RoomMovie>

    @Query(
        "SELECT * FROM movies WHERE (title LIKE '%' || :searchString || '%') OR" +
            "(address LIKE '%' || :searchString || '%') OR" +
            "(year LIKE '%' || :searchString || '%')" +
            " LIMIT :pageSize OFFSET :rowNumber"
    )
    fun getPaged(rowNumber: Int, pageSize: Int, searchString: String): List<RoomMovie>

    @Query("SELECT * FROM movies WHERE lat>:lat1 AND lon>:lon1 AND lat<:lat2 AND lon<:lon2 LIMIT :numberOfResults")
    fun getByPosition(
        lat1: Double,
        lon1: Double,
        lat2: Double,
        lon2: Double,
        numberOfResults: Int
    ): List<RoomMovie>
}
