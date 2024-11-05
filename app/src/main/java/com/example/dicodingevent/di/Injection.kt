package com.example.dicodingevent.di

import android.content.Context
import com.example.dicodingevent.data.local.EventDatabase
import com.example.dicodingevent.data.network.ApiConfig
import com.example.dicodingevent.repository.Repository
import com.example.dicodingevent.ui.settings.SettingsPreferences
import com.example.dicodingevent.ui.settings.dataStore

object Injection {
    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        val database = EventDatabase.getDatabase(context)
        val dao = database.favoriteDao()
        val pref = SettingsPreferences.getInstance(context.dataStore)
        return Repository.getInstance(apiService, dao, pref)
    }
}