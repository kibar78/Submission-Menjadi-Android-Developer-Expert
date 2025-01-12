package com.example.dicodingevent.core.domain.usecase

import kotlinx.coroutines.flow.Flow

interface DataStoreUseCase {
    // Mendapatkan pengaturan tema
    fun getThemeSettings(): Flow<Boolean>

    // Menyimpan pengaturan tema
    suspend fun saveThemeSetting(isDarkModeActive: Boolean)

    // Mendapatkan pengaturan notifikasi
    fun getNotificationSettings(): Flow<Boolean>

    // Menyimpan pengaturan notifikasi
    suspend fun saveNotificationSetting(isNotificationActive: Boolean)

}