package com.example.dicodingevent.core.data.source.remote

import com.example.dicodingevent.core.data.source.remote.network.ApiService
import com.example.dicodingevent.core.data.source.remote.response.DetailEventResponse
import com.example.dicodingevent.core.data.source.remote.response.EventsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Call

class RemoteDataSource private constructor(private val apiService: ApiService){

    companion object{
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(service: ApiService): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(service)
            }
    }
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

    fun getDailyReminder(active: Int, limit: Int): Call<EventsResponse> {
        return apiService.dailyReminder(active, limit)
    }
}