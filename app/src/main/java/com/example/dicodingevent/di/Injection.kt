package com.example.dicodingevent.di

import android.content.Context
import com.example.dicodingevent.data.network.ApiConfig
import com.example.dicodingevent.repository.Repository

object Injection {
    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        return Repository.getInstance(apiService)
    }
}