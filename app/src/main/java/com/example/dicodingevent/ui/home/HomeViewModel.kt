package com.example.dicodingevent.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingevent.core.data.source.ResultState
import com.example.dicodingevent.core.domain.model.Events
import com.example.dicodingevent.core.domain.usecase.EventsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel(private val useCase: EventsUseCase) : ViewModel() {
    private val _listUpcomingEvents = MutableStateFlow<ResultState<List<Events>>>(ResultState.Loading)
    val listUpcomingEvents: StateFlow<ResultState<List<Events>>> = _listUpcomingEvents

    private val _listFinishedEvents = MutableLiveData<ResultState<List<Events>>>(ResultState.Loading)
    val listFinishedEvents: LiveData<ResultState<List<Events>>> = _listFinishedEvents

    fun getupComingEvents(query: Int = 1){
        viewModelScope.launch {
            useCase.getListEvents(query)
                .onStart { _listUpcomingEvents.value = ResultState.Loading }
                .catch { e -> _listUpcomingEvents.value = ResultState.Error(e.message.toString()) }
                .collect { events ->
                    _listUpcomingEvents.value = ResultState.Success(events.take(5))
                }
        }
    }

    fun getFinishedEvents(query: Int = 0) {
        viewModelScope.launch {
            useCase.getListEvents(query)
                .onStart { _listFinishedEvents.value = ResultState.Loading }
                .catch { e -> _listFinishedEvents.value = ResultState.Error(e.message.toString()) }
                .collect { events ->
                    _listFinishedEvents.value = ResultState.Success(events.take(5))
                }
        }
    }
}