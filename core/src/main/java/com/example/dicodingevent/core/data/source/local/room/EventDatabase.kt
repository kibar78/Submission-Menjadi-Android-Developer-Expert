package com.example.dicodingevent.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dicodingevent.core.data.source.local.entity.FavoriteEntity

@Database(
    entities = [FavoriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class EventDatabase : RoomDatabase(){
    abstract fun favoriteDao(): FavoriteDao
}