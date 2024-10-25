package com.example.dicodingevent.ui.finished

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingevent.data.network.response.ListEventsItem
import com.example.dicodingevent.repository.Repository
import com.example.dicodingevent.utils.ResultState
import kotlinx.coroutines.launch

class FinishedViewModel(private val repository: Repository) : ViewModel() {

    private val _listFinishedEvents = MutableLiveData<ResultState<List<ListEventsItem>>>()
    val listFinishedEvents: MutableLiveData<ResultState<List<ListEventsItem>>> = _listFinishedEvents

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFinishedEvents(query: Int = 0){
        _listFinishedEvents.value = ResultState.Loading
        viewModelScope.launch {
            try {
                _listFinishedEvents.value = ResultState.Success(repository.getListEvents(query).listEvents)
            }catch (e: Exception){
                _listFinishedEvents.value = ResultState.Error(e.message.toString())
            }
        }
    }
}