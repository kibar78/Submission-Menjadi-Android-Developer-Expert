package com.example.dicodingevent.core.data.source.local

import com.example.dicodingevent.core.data.source.local.entity.FavoriteEntity
import com.example.dicodingevent.core.data.source.local.room.FavoriteDao
import kotlinx.coroutines.flow.Flow

class RoomDataSource(private val favoriteDao: FavoriteDao){

    suspend fun insert(favorite: FavoriteEntity) {
        favoriteDao.insert(favorite)
    }

    suspend fun delete(favorite: FavoriteEntity) {
        favoriteDao.delete(favorite)
    }

    fun getFavoriteById(id: String): Flow<FavoriteEntity?> {
        return favoriteDao.getFavoriteEventById(id)
    }

    fun getAllFavorites(): Flow<List<FavoriteEntity>> {
        return favoriteDao.getAllFavorite()
    }
}