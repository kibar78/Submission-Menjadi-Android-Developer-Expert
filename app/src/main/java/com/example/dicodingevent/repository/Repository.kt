package com.example.dicodingevent.repository

import androidx.lifecycle.LiveData
import com.example.dicodingevent.data.local.FavoriteDao
import com.example.dicodingevent.data.local.FavoriteEntity
import com.example.dicodingevent.data.network.ApiService
import com.example.dicodingevent.data.network.response.DetailEventResponse
import com.example.dicodingevent.data.network.response.EventsResponse

class Repository private constructor(
    private val apiService: ApiService,
    private val favoriteDao: FavoriteDao
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

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService,
            favoriteDao: FavoriteDao
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService, favoriteDao)
            }.also { instance = it }
    }
}