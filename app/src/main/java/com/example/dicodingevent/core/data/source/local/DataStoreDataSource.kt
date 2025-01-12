package com.example.dicodingevent.core.data.source.local

import com.example.dicodingevent.core.data.source.local.datastore.SettingsPreferences
import kotlinx.coroutines.flow.Flow

class DataStoreDataSource(private val settingsPreferences: SettingsPreferences) {
    fun getThemeSetting(): Flow<Boolean> {
        return settingsPreferences.getThemeSetting()
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        settingsPreferences.saveThemeSetting(isDarkModeActive)
    }

    fun getNotificationSetting(): Flow<Boolean> {
        return settingsPreferences.getNotificationSetting()
    }

    suspend fun saveNotificationSetting(isNotificationActive: Boolean) {
        settingsPreferences.saveNotificationSetting(isNotificationActive)
    }
}