package com.example.dicodingevent.core.domain.usecase

import com.example.dicodingevent.core.domain.model.Events
import com.example.dicodingevent.core.domain.repository.IRemoteRepository
import kotlinx.coroutines.flow.Flow

class RemoteInteractor(private val repository: IRemoteRepository): RemoteUseCase{
    override fun getListEvents(query: Int): Flow<List<Events>> {
        return repository.getListEvents(query)
    }

    override fun searchEvents(active: Int, query: String): Flow<List<Events>> {
        return repository.searchEvents(active, query)
    }

    override fun getDetailEvent(id: Int): Flow<Events?> {
        return repository.getDetailEvent(id)
    }

    override suspend fun getDailyReminder(active: Int, limit: Int): List<Events> {
        return repository.getDailyReminder(active,limit)
    }
}