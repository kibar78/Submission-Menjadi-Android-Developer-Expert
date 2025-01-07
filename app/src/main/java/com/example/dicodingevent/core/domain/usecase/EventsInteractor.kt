package com.example.dicodingevent.core.domain.usecase

import com.example.dicodingevent.core.domain.model.Events
import com.example.dicodingevent.core.domain.model.Favorite
import com.example.dicodingevent.core.domain.repository.IEventsRepository
import kotlinx.coroutines.flow.Flow

class EventsInteractor(private val repository: IEventsRepository): EventsUseCase{
    override fun getListEvents(query: Int): Flow<List<Events>> {
        return repository.getListEvents(query)
    }

    override fun searchEvents(active: Int, query: String): Flow<List<Events>> {
        return repository.searchEvents(active, query)
    }

    override fun getDetailEvent(id: Int): Flow<Events?> {
        return repository.getDetailEvent(id)
    }

    override suspend fun insertFavorite(favorite: Favorite) {
        repository.insertFavorite(favorite)
    }

    override suspend fun deleteFavorite(favorite: Favorite) {
        repository.deleteFavorite(favorite)
    }

    override fun getFavoriteById(id: String): Flow<Favorite?> {
        return repository.getFavoriteById(id)
    }

    override fun getAllFavorites(): Flow<List<Favorite>> {
        return repository.getAllFavorites()
    }

    override fun getThemeSettings(): Flow<Boolean> {
        return repository.getThemeSettings()
    }

    override suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        repository.saveThemeSetting(isDarkModeActive)
    }

    override fun getNotificationSettings(): Flow<Boolean> {
        return repository.getNotificationSettings()
    }

    override suspend fun saveNotificationSetting(isNotificationActive: Boolean) {
        repository.saveNotificationSetting(isNotificationActive)
    }
}