package com.example.dicodingevent.core.di

import android.content.Context
import com.example.dicodingevent.core.data.source.Repository
import com.example.dicodingevent.core.data.source.local.LocalDataSource
import com.example.dicodingevent.core.data.source.local.datastore.SettingsPreferences
import com.example.dicodingevent.core.data.source.local.datastore.dataStore
import com.example.dicodingevent.core.data.source.local.room.EventDatabase
import com.example.dicodingevent.core.data.source.remote.RemoteDataSource
import com.example.dicodingevent.core.data.source.remote.network.ApiConfig
import com.example.dicodingevent.core.domain.repository.IEventsRepository
import com.example.dicodingevent.core.domain.usecase.EventsInteractor
import com.example.dicodingevent.core.domain.usecase.EventsUseCase

object Injection {
    fun provideRepository(context: Context): IEventsRepository {
        val database = EventDatabase.getDatabase(context)
        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.getApiService())
        val settingsPreferences = SettingsPreferences.getInstance(context.dataStore)
        val localDataSource = LocalDataSource.getInstance(
            favoriteDao = database.favoriteDao(),
            settingsPreferences = settingsPreferences
        )
        return Repository.getInstance(remoteDataSource, localDataSource)
    }
    fun provideEventsUseCase(context: Context): EventsUseCase {
        val repository = provideRepository(context)
        return EventsInteractor(repository)
    }
}