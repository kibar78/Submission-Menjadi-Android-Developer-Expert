package com.example.dicodingevent.data.network

import com.example.dicodingevent.data.network.response.EventsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("events")
    suspend fun getListEvents(@Query("active")query: Int): EventsResponse

    @GET("events")
    suspend fun searchEvents(
        @Query("active") active: Int,
        @Query("q") query: String
    ): EventsResponse
}