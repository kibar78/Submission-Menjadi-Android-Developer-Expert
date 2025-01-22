package com.example.dicodingevent.core.data.source.remote

import com.example.dicodingevent.core.data.source.remote.network.ApiService
import com.example.dicodingevent.core.data.source.remote.response.DetailEventResponse
import com.example.dicodingevent.core.data.source.remote.response.EventsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteDataSource(private val apiService: ApiService){

    fun getListEvents(active: Int): Flow<EventsResponse> {
        return flow {
            emit(apiService.getListEvents(active))
        }
    }

    fun searchEvents(active: Int, query: String): Flow<EventsResponse> {
        return flow {
            emit(apiService.searchEvents(active, query))
        }
    }

    fun getDetailEvent(id: Int): Flow<DetailEventResponse> {
        return flow {
            emit(apiService.getDetailEvent(id))
        }
    }

    suspend fun getDailyReminder(active: Int, limit: Int): EventsResponse {
        return apiService.dailyReminder(active,limit)
    }
}