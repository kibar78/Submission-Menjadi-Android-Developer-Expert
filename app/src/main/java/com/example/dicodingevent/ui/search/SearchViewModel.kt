package com.example.dicodingevent.ui.search

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

class SearchViewModel(private val useCase: EventsUseCase) : ViewModel() {

    private val _listSearchEvents = MutableStateFlow<ResultState<List<Events>>>(ResultState.Loading)
    val listSearchEvents: StateFlow<ResultState<List<Events>>> = _listSearchEvents

    fun searchEvents(active: Int, query: String) {
        viewModelScope.launch {
            useCase.searchEvents(active, query)
                .onStart { _listSearchEvents.value = ResultState.Loading }
                .catch { e -> _listSearchEvents.value = ResultState.Error(e.message.toString()) }
                .collect { events ->
                    _listSearchEvents.value = ResultState.Success(events)
                }
        }
    }

    companion object{
        const val QUERY = "devcoach"
        const val ACTIVE = -1
    }
}