package com.example.dicodingevent.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.dicodingevent.core.domain.usecase.EventsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SettingsViewModel(private val useCase: EventsUseCase): ViewModel() {
    val themeSetting: Flow<Boolean> = useCase.getThemeSettings()

    fun getThemeSetting(): LiveData<Boolean> {
        return useCase.getThemeSettings().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            useCase.saveThemeSetting(isDarkModeActive)
        }
    }

    fun getNotificationSetting(): LiveData<Boolean> {
        return useCase.getNotificationSettings().asLiveData()
    }

    fun saveNotificationSetting(isNotificationActive: Boolean) {
        viewModelScope.launch {
            useCase.saveNotificationSetting(isNotificationActive)
        }
    }
}