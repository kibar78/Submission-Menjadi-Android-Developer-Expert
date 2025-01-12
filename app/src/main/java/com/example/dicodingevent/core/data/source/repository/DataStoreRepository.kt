package com.example.dicodingevent.core.data.source.repository

import com.example.dicodingevent.core.data.source.local.DataStoreDataSource
import com.example.dicodingevent.core.domain.repository.IDataStoreRepository
import kotlinx.coroutines.flow.Flow

class DataStoreRepository (private val dataStoreDataSource: DataStoreDataSource): IDataStoreRepository {

    override fun getThemeSettings(): Flow<Boolean> {
        return dataStoreDataSource.getThemeSetting()
    }

    override suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStoreDataSource.saveThemeSetting(isDarkModeActive)
    }

    override fun getNotificationSettings(): Flow<Boolean> {
        return dataStoreDataSource.getNotificationSetting()
    }

    override suspend fun saveNotificationSetting(isNotificationActive: Boolean) {
        dataStoreDataSource.saveNotificationSetting(isNotificationActive)
    }
}