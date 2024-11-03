package com.example.dicodingevent.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingevent.data.local.FavoriteEntity
import com.example.dicodingevent.data.network.response.Event
import com.example.dicodingevent.repository.Repository
import com.example.dicodingevent.utils.ResultState
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: Repository): ViewModel() {

    private val _detailEvent = MutableLiveData<ResultState<Event?>>()
    val detailEvent: LiveData<ResultState<Event?>> = _detailEvent

    fun getDetailEvent(id: Int) {
        if (_detailEvent.value == null) {
            viewModelScope.launch {
                _detailEvent.value = ResultState.Loading
                try {
                    val response = repository.getDetailEvent(id)
                    _detailEvent.value = ResultState.Success(response.event)
                } catch (e: Exception) {
                    _detailEvent.value = ResultState.Error(e.message.toString())
                }
            }
        }
    }

    fun addFavorite(event: Event) {
        viewModelScope.launch {
                    val favoriteAdd = FavoriteEntity(
                        id = event.id.toString(),
                        name = event.name.toString(),
                        mediaCover = event.mediaCover
                    )
                    repository.insert(favoriteAdd)
            }
        }

    fun deleteFavorite(event: Event) {
        viewModelScope.launch {
                val favoriteDelete = FavoriteEntity(
                    id = event.id.toString(),
                    name = event.name.toString(),
                    mediaCover = event.mediaCover
                )
                repository.delete(favoriteDelete)
        }
    }

    fun getFavoriteById(id: String) : LiveData<FavoriteEntity> {
        return repository.getFavoriteById(id)
    }

}