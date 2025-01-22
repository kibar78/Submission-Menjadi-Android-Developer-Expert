package com.example.dicodingevent.core.domain.usecase

import com.example.dicodingevent.core.domain.model.Favorite
import com.example.dicodingevent.core.domain.repository.IRoomRepository
import kotlinx.coroutines.flow.Flow

class RoomInteractor(private val repository: IRoomRepository): RoomUseCase{
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
}