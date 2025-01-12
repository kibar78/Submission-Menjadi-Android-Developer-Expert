package com.example.dicodingevent.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingevent.core.utils.ResultState
import com.example.dicodingevent.core.domain.model.Events
import com.example.dicodingevent.core.domain.model.Favorite
import com.example.dicodingevent.core.domain.usecase.RemoteUseCase
import com.example.dicodingevent.core.domain.usecase.RoomUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailViewModel(
    private val remoteUseCase: RemoteUseCase,
    private val roomUseCase: RoomUseCase): ViewModel() {

    private val _detailEvent = MutableStateFlow<ResultState<Events?>>(ResultState.Loading)
    val detailEvent: StateFlow<ResultState<Events?>> = _detailEvent

    fun getDetailEvent(id: Int) {
        viewModelScope.launch {
            remoteUseCase.getDetailEvent(id)
                .onStart { _detailEvent.value = ResultState.Loading }
                .catch { e -> _detailEvent.value = ResultState.Error(e.message.toString()) }
                .collect { event ->
                    _detailEvent.value = ResultState.Success(event)
                }
        }
    }

    fun addFavorite(event: Events) {
        viewModelScope.launch {
                    val favoriteAdd = Favorite(
                        id = event.id.toString(),
                        name = event.name,
                        mediaCover = event.mediaCover
                    )
                    roomUseCase.insertFavorite(favoriteAdd)
            }
        }

    fun deleteFavorite(event: Events) {
        viewModelScope.launch {
                val favoriteDelete = Favorite(
                    id = event.id.toString(),
                    name = event.name,
                    mediaCover = event.mediaCover
                )
                roomUseCase.deleteFavorite(favoriteDelete)
        }
    }

    fun getFavoriteById(id: String) : Flow<Favorite?> {
        return roomUseCase.getFavoriteById(id)
    }
}