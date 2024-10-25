package com.example.dicodingevent.ui.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingevent.data.network.response.ListEventsItem
import com.example.dicodingevent.repository.Repository
import com.example.dicodingevent.utils.ResultState
import kotlinx.coroutines.launch

class UpcomingViewModel(private val repository: Repository) : ViewModel() {

    private val _listUpcomingEvents = MutableLiveData<ResultState<List<ListEventsItem>>>()
    val listUpcomingEvents: MutableLiveData<ResultState<List<ListEventsItem>>> = _listUpcomingEvents

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getupComingEvents(query: Int = 1){
        _listUpcomingEvents.value = ResultState.Loading
        viewModelScope.launch {
            try {
                _listUpcomingEvents.value = ResultState.Success(repository.getListEvents(query).listEvents)
            }catch (e: Exception){
                _listUpcomingEvents.value = ResultState.Error(e.message.toString())
            }
        }
    }

}