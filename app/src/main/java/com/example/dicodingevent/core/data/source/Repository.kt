package com.example.dicodingevent.core.data.source

import com.example.dicodingevent.core.data.source.local.LocalDataSource
import com.example.dicodingevent.core.data.source.remote.RemoteDataSource
import com.example.dicodingevent.core.domain.model.Events
import com.example.dicodingevent.core.domain.model.Favorite
import com.example.dicodingevent.core.domain.repository.IEventsRepository
import com.example.dicodingevent.core.utils.EventMapper
import com.example.dicodingevent.core.utils.FavoriteMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Repository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): IEventsRepository {
    // Mengambil daftar event dari Remote dan memetakan ke domain model
    override fun getListEvents(query: Int): Flow<List<Events>> {
        return remoteDataSource.getListEvents(query).map {
            EventMapper.mapEventsResponseToDomain(it)
        }
    }

    // Mencari event berdasarkan query dan memetakan ke domain model
    override fun searchEvents(active: Int, query: String): Flow<List<Events>> {
       return remoteDataSource.searchEvents(active, query).map {
           EventMapper.mapEventsResponseToDomain(it)
       }
    }

    // Mendapatkan detail event langsung dari Remote
    override fun getDetailEvent(id: Int): Flow<Events> {
        return remoteDataSource.getDetailEvent(id).map {
            EventMapper.mapDetailEventResponseToDomain(it)
        }
    }

    // Menambahkan event ke favorit
    override suspend fun insertFavorite(favorite: Favorite) {
        val entity = FavoriteMapper.mapDomainToEntity(favorite)
        localDataSource.insert(entity)
    }

    // Menghapus event dari favorit
    override suspend fun deleteFavorite(favorite: Favorite) {
        val entity = FavoriteMapper.mapDomainToEntity(favorite)
        localDataSource.delete(entity)
    }

    // Mendapatkan detail favorit berdasarkan ID
    override fun getFavoriteById(id: String): Flow<Favorite?> {
        return localDataSource.getFavoriteById(id).map { entity ->
            entity?.let { FavoriteMapper.mapEntityToDomain(it) }
        }
    }

    // Mendapatkan semua favorit
    override fun getAllFavorites(): Flow<List<Favorite>> {
        return localDataSource.getAllFavorites()
            .map { entities -> FavoriteMapper.mapEntitiesToDomain(entities) }
    }

    // Mendapatkan pengaturan tema
    override fun getThemeSettings(): Flow<Boolean> {
        return localDataSource.getThemeSetting()
    }

    // Menyimpan pengaturan tema
    override suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        localDataSource.saveThemeSetting(isDarkModeActive)
    }

    // Mendapatkan pengaturan notifikasi
    override fun getNotificationSettings(): Flow<Boolean> {
        return localDataSource.getNotificationSetting()
    }

    // Menyimpan pengaturan notifikasi
    override suspend fun saveNotificationSetting(isNotificationActive: Boolean) {
        localDataSource.saveNotificationSetting(isNotificationActive)
    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            remoteDataSource: RemoteDataSource,
            localDataSource: LocalDataSource
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(remoteDataSource, localDataSource)
            }.also { instance = it }
    }
}