package com.example.dicodingevent.core.domain.repository

import com.example.dicodingevent.core.domain.model.Events
import com.example.dicodingevent.core.domain.model.Favorite
import kotlinx.coroutines.flow.Flow

interface IEventsRepository {
    // Mendapatkan daftar event
    fun getListEvents(query: Int): Flow<List<Events>>

    // Mencari event berdasarkan query
    fun searchEvents(active: Int, query: String): Flow<List<Events>>

    // Mendapatkan detail event
    fun getDetailEvent(id: Int): Flow<Events?>

    // Menambahkan event ke favorit
    suspend fun insertFavorite(favorite: Favorite)

    // Menghapus event dari favorit
    suspend fun deleteFavorite(favorite: Favorite)

    // Mendapatkan detail favorit berdasarkan ID
    fun getFavoriteById(id: String): Flow<Favorite?>

    // Mendapatkan semua favorit
    fun getAllFavorites(): Flow<List<Favorite>>

    // Mendapatkan pengaturan tema
    fun getThemeSettings(): Flow<Boolean>

    // Menyimpan pengaturan tema
    suspend fun saveThemeSetting(isDarkModeActive: Boolean)

    // Mendapatkan pengaturan notifikasi
    fun getNotificationSettings(): Flow<Boolean>

    // Menyimpan pengaturan notifikasi
    suspend fun saveNotificationSetting(isNotificationActive: Boolean)
}