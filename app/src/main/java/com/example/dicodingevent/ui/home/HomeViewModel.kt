package com.example.dicodingevent.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingevent.data.network.response.ListEventsItem
import com.example.dicodingevent.repository.Repository
import com.example.dicodingevent.utils.ResultState
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository) : ViewModel() {
    private val _listUpcomingEvents = MutableLiveData<ResultState<List<ListEventsItem>>>()
    val listUpcomingEvents: LiveData<ResultState<List<ListEventsItem>>> = _listUpcomingEvents

    private val _listFinishedEvents = MutableLiveData<ResultState<List<ListEventsItem>>>()
    val listFinishedEvents: LiveData<ResultState<List<ListEventsItem>>> = _listFinishedEvents

    fun getupComingEvents(query: Int = 1){
            if (_listUpcomingEvents.value == null){
            viewModelScope.launch {
                _listUpcomingEvents.value = ResultState.Loading
                try {
                    val events = repository.getListEvents(query).listEvents
                    _listUpcomingEvents.value = ResultState.Success(events.take(5))
                } catch (e: Exception) {
                    _listUpcomingEvents.value = ResultState.Error(e.message.toString())
                }
            }
        }
    }

    fun getFinishedEvents(query: Int = 0) {
        if (_listFinishedEvents.value == null) {
            viewModelScope.launch {
                _listFinishedEvents.value = ResultState.Loading
                try {
                    val events = repository.getListEvents(query).listEvents
                    _listFinishedEvents.value = ResultState.Success(events.take(5))
                } catch (e: Exception) {
                    _listFinishedEvents.value = ResultState.Error(e.message.toString())
                }
            }
        }
    }
}