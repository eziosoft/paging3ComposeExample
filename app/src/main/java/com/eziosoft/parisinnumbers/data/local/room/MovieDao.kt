package com.eziosoft.parisinnumbers.data.local.room

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

    @Query(
        "SELECT * FROM movies WHERE (title LIKE '%' || :searchString || '%') OR" +
            "(address LIKE '%' || :searchString || '%') OR" +
            "(year LIKE '%' || :searchString || '%')" +
            " LIMIT :pageSize OFFSET :rowNumber"
    )
    fun getPaged(rowNumber: Int, pageSize: Int, searchString: String): List<RoomMovie>
}
