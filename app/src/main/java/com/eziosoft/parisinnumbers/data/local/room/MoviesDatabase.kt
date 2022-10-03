package com.eziosoft.parisinnumbers.data.local.room

import androidx.room.RoomDatabase

@androidx.room.Database(entities = [RoomMovie::class], version = 1)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
