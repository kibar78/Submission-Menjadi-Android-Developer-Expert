package com.example.dicodingevent.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_event")
data class FavoriteEntity(
    @PrimaryKey val id: String,
    val name: String = "",
    val mediaCover: String? = null
)