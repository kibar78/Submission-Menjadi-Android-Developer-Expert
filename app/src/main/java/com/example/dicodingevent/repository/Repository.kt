package com.example.dicodingevent.repository

import com.example.dicodingevent.data.network.ApiService
import com.example.dicodingevent.data.network.response.EventsResponse

class Repository private constructor(
    private val apiService: ApiService,
){
    suspend fun getListEvents(query: Int): EventsResponse{
        return apiService.getListEvents(query)
    }

    suspend fun searchEvents(active: Int, query: String): EventsResponse{
        return apiService.searchEvents(active, query)
    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService,
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService)
            }.also { instance = it }
    }
}