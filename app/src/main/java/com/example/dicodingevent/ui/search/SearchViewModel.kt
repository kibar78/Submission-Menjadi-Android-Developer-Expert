package com.example.dicodingevent.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingevent.data.network.response.ListEventsItem
import com.example.dicodingevent.repository.Repository
import com.example.dicodingevent.utils.ResultState
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: Repository) : ViewModel() {

    private val _listSearchEvents = MutableLiveData<ResultState<List<ListEventsItem>>>()
    val listSearchEvents: MutableLiveData<ResultState<List<ListEventsItem>>> = _listSearchEvents

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun searchEvents(active: Int, query: String){
        _listSearchEvents.value = ResultState.Loading
        viewModelScope.launch {
            try {
                _listSearchEvents.value = ResultState.Success(repository.searchEvents(active, query).listEvents)
            }catch (e: Exception){
                _listSearchEvents.value = ResultState.Error(e.message.toString())
            }
        }
    }
    companion object{
        const val QUERY = "devcoach"
        const val ACTIVE = -1
    }
}