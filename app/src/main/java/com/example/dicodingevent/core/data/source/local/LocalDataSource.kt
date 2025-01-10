package com.example.dicodingevent.core.data.source.local

import com.example.dicodingevent.core.data.source.local.datastore.SettingsPreferences
import com.example.dicodingevent.core.data.source.local.entity.FavoriteEntity
import com.example.dicodingevent.core.data.source.local.room.FavoriteDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(
    private val favoriteDao: FavoriteDao,
    private val settingsPreferences: SettingsPreferences
){

//    companion object{
//        private var instance: LocalDataSource? = null
//        fun getInstance(favoriteDao: FavoriteDao,
//                        settingsPreferences: SettingsPreferences
//        ): LocalDataSource =
//            instance ?: synchronized(this){
//                instance ?: LocalDataSource(favoriteDao, settingsPreferences)
//            }
//    }

    // Fungsi untuk menambahkan favorite ke database
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

    // Fungsi untuk mengambil tema dari DataStore
    fun getThemeSetting(): Flow<Boolean> {
        return settingsPreferences.getThemeSetting()
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        settingsPreferences.saveThemeSetting(isDarkModeActive)
    }

    fun getNotificationSetting(): Flow<Boolean> {
        return settingsPreferences.getNotificationSetting()
    }

    suspend fun saveNotificationSetting(isNotificationActive: Boolean) {
        settingsPreferences.saveNotificationSetting(isNotificationActive)
    }
}