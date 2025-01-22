package com.example.dicodingevent.core.domain.repository

import com.example.dicodingevent.core.domain.model.Events
import kotlinx.coroutines.flow.Flow

interface IRemoteRepository {
    // Mendapatkan daftar event
    fun getListEvents(query: Int): Flow<List<Events>>

    // Mencari event berdasarkan query
    fun searchEvents(active: Int, query: String): Flow<List<Events>>

    // Mendapatkan detail event
    fun getDetailEvent(id: Int): Flow<Events?>

    // Notifikasi
    suspend fun getDailyReminder(active: Int, limit: Int): List<Events>
}