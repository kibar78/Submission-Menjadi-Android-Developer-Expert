package com.example.dicodingevent.core.domain.usecase

import com.example.dicodingevent.core.domain.model.Favorite
import kotlinx.coroutines.flow.Flow

interface RoomUseCase {
    // Menambahkan event ke favorit
    suspend fun insertFavorite(favorite: Favorite)

    // Menghapus event dari favorit
    suspend fun deleteFavorite(favorite: Favorite)

    // Mendapatkan detail favorit berdasarkan ID
    fun getFavoriteById(id: String): Flow<Favorite?>

    // Mendapatkan semua favorit
    fun getAllFavorites(): Flow<List<Favorite>>
}