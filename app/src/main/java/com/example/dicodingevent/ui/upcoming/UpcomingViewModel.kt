package com.example.dicodingevent.ui.upcoming

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

class UpcomingViewModel(private val useCase: RemoteUseCase) : ViewModel() {

    private val _listUpcomingEvents = MutableStateFlow<ResultState<List<Events>>>(ResultState.Loading)
    val listUpcomingEvents: StateFlow<ResultState<List<Events>>> = _listUpcomingEvents

    fun getupComingEvents(query: Int = 1){
        viewModelScope.launch {
            useCase.getListEvents(query)
                .onStart { _listUpcomingEvents.value = ResultState.Loading }
                .catch { e-> _listUpcomingEvents.value = ResultState.Error(e.message.toString())}
                .collect{events->
                    _listUpcomingEvents.value = ResultState.Success(events)
                }
        }
    }

}