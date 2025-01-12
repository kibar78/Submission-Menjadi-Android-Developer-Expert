package com.example.dicodingevent.core.data.source.repository

import com.example.dicodingevent.core.data.source.local.RoomDataSource
import com.example.dicodingevent.core.domain.model.Favorite
import com.example.dicodingevent.core.domain.repository.IRoomRepository
import com.example.dicodingevent.core.utils.FavoriteMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomRepository(private val roomDataSource: RoomDataSource): IRoomRepository {

    override suspend fun insertFavorite(favorite: Favorite) {
        val entity = FavoriteMapper.mapDomainToEntity(favorite)
        roomDataSource.insert(entity)
    }

    override suspend fun deleteFavorite(favorite: Favorite) {
        val entity = FavoriteMapper.mapDomainToEntity(favorite)
        roomDataSource.delete(entity)
    }

    override fun getFavoriteById(id: String): Flow<Favorite?> {
        return roomDataSource.getFavoriteById(id).map { entity ->
            entity?.let { FavoriteMapper.mapEntityToDomain(it) }
        }
    }

    override fun getAllFavorites(): Flow<List<Favorite>> {
        return roomDataSource.getAllFavorites()
            .map { entities -> FavoriteMapper.mapEntitiesToDomain(entities) }
    }
}