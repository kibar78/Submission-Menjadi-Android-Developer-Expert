package com.example.dicodingevent.ui.finished

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingevent.core.utils.ResultState
import com.example.dicodingevent.core.domain.model.Events
import com.example.dicodingevent.core.domain.usecase.RemoteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class FinishedViewModel(private val useCase: RemoteUseCase) : ViewModel() {

    private val _listFinishedEvents = MutableStateFlow<ResultState<List<Events>>>(ResultState.Loading)
    val listFinishedEvents: StateFlow<ResultState<List<Events>>> = _listFinishedEvents

    fun getFinishedEvents(query: Int = 0) {
        viewModelScope.launch {
            useCase.getListEvents(query)
                .onStart { _listFinishedEvents.value = ResultState.Loading }
                .catch { e -> _listFinishedEvents.value = ResultState.Error(e.message.toString()) }
                .collect{ events ->
                    _listFinishedEvents.value = ResultState.Success(events)
                }
        }
    }
}