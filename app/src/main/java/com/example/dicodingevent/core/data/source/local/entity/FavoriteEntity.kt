package com.example.dicodingevent.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_event")
data class FavoriteEntity(
    @PrimaryKey val id: String,
    val name: String = "",
    val mediaCover: String? = null
)