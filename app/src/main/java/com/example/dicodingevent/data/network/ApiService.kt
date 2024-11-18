package com.example.dicodingevent.data.network

import com.example.dicodingevent.data.network.response.DetailEventResponse
import com.example.dicodingevent.data.network.response.EventsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("events")
    suspend fun getListEvents(@Query("active")query: Int): EventsResponse

    @GET("events")
    suspend fun searchEvents(
        @Query("active") active: Int,
        @Query("q") query: String
    ): EventsResponse

    @GET("events/{id}")
    suspend fun getDetailEvent(@Path("id")id: Int): DetailEventResponse

    @GET("events")
    fun dailyReminder(
        @Query("active") active: Int,
        @Query("limit") limit: Int
    ): Call<EventsResponse>
}