package com.example.dicodingevent.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingevent.core.domain.model.Favorite
import com.example.dicodingevent.core.domain.usecase.EventsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(private val useCase: EventsUseCase) : ViewModel() {
    private val _listFavorite = MutableStateFlow<List<Favorite>>(emptyList())
    val listFavorite : StateFlow<List<Favorite>> = _listFavorite

    fun getAllFavorite(){
        viewModelScope.launch {
            useCase.getAllFavorites().collect{favorite ->
                _listFavorite.value = favorite
            }
        }
    }
}