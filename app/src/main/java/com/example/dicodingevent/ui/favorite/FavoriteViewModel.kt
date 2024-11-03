package com.example.dicodingevent.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingevent.data.local.FavoriteEntity
import com.example.dicodingevent.repository.Repository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: Repository) : ViewModel() {
    private val _listFavorite = MutableLiveData<List<FavoriteEntity>>()
    val listFavorite : LiveData<List<FavoriteEntity>> = _listFavorite

    fun getAllFavorite(){
        viewModelScope.launch {
            _listFavorite.value = repository.getAllFavorite()
        }
    }
}