package com.example.dicodingevent.repository

import androidx.lifecycle.LiveData
import com.example.dicodingevent.data.local.FavoriteDao
import com.example.dicodingevent.data.local.FavoriteEntity
import com.example.dicodingevent.data.network.ApiService
import com.example.dicodingevent.data.network.response.DetailEventResponse
import com.example.dicodingevent.data.network.response.EventsResponse
import com.example.dicodingevent.ui.settings.SettingsPreferences
import kotlinx.coroutines.flow.Flow

class Repository private constructor(
    private val apiService: ApiService,
    private val favoriteDao: FavoriteDao,
    private val pref: SettingsPreferences
){
    suspend fun getListEvents(query: Int): EventsResponse{
        return apiService.getListEvents(query)
    }

    suspend fun searchEvents(active: Int, query: String): EventsResponse{
        return apiService.searchEvents(active, query)
    }

    suspend fun getDetailEvent(id: Int): DetailEventResponse{
        return apiService.getDetailEvent(id)
    }

    suspend fun insert(favorite: FavoriteEntity) = favoriteDao.insert(favorite)

    suspend fun delete(favorite: FavoriteEntity) = favoriteDao.delete(favorite)

    fun getFavoriteById(id: String) : LiveData<FavoriteEntity>{
      return favoriteDao.getFavoriteEventById(id)
    }

    suspend fun getAllFavorite(): List<FavoriteEntity>{
        return favoriteDao.getAllFavorite()
    }

    fun getThemeSettings(): Flow<Boolean> {
        return pref.getThemeSetting()
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        pref.saveThemeSetting(isDarkModeActive)
    }

    fun getNotificationSetting(): Flow<Boolean>{
        return pref.getNotificationSetting()
    }

    suspend fun saveNotificationSetting(isNotificationActive: Boolean){
        pref.saveNotificationSetting(isNotificationActive)
    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService,
            favoriteDao: FavoriteDao,
            pref: SettingsPreferences
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService, favoriteDao, pref)
            }.also { instance = it }
    }
}