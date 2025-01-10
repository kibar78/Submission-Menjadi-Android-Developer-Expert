package com.example.dicodingevent

import android.app.Application
import com.example.dicodingevent.core.di.databaseModule
import com.example.dicodingevent.core.di.networkModule
import com.example.dicodingevent.core.di.repositoryModule
import com.example.dicodingevent.core.di.settingsModule
import com.example.dicodingevent.di.useCaseModule
import com.example.dicodingevent.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    settingsModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}