package com.example.dicodingevent.core.di

import androidx.room.Room
import com.example.dicodingevent.core.data.source.Repository
import com.example.dicodingevent.core.data.source.local.LocalDataSource
import com.example.dicodingevent.core.data.source.local.datastore.SettingsPreferences
import com.example.dicodingevent.core.data.source.local.datastore.dataStore
import com.example.dicodingevent.core.data.source.local.room.EventDatabase
import com.example.dicodingevent.core.data.source.remote.RemoteDataSource
import com.example.dicodingevent.core.data.source.remote.network.ApiService
import com.example.dicodingevent.core.domain.repository.IEventsRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<EventDatabase>().favoriteDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            EventDatabase::class.java, "Event.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val settingsModule = module {
    single {
        SettingsPreferences(androidContext().dataStore)
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120,TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://event-api.dicoding.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get(), get()) }
    single { RemoteDataSource(get()) }
    single <IEventsRepository>{ Repository(get(),get()) }
}