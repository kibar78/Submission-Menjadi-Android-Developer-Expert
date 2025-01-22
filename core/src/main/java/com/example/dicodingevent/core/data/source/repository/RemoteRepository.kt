package com.example.dicodingevent.core.data.source.repository

import com.example.dicodingevent.core.data.source.remote.RemoteDataSource
import com.example.dicodingevent.core.domain.model.Events
import com.example.dicodingevent.core.domain.repository.IRemoteRepository
import com.example.dicodingevent.core.utils.EventMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RemoteRepository(private val remoteDataSource: RemoteDataSource): IRemoteRepository {
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

    override suspend fun getDailyReminder(active: Int, limit: Int): List<Events> {
        val response = remoteDataSource.getDailyReminder(active, limit)
        return EventMapper.mapEventsResponseToDomain(response)
        }
    }
