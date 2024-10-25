package com.example.dicodingevent.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dicodingevent.di.Injection
import com.example.dicodingevent.repository.Repository
import com.example.dicodingevent.ui.finished.FinishedViewModel
import com.example.dicodingevent.ui.home.HomeViewModel
import com.example.dicodingevent.ui.search.SearchViewModel
import com.example.dicodingevent.ui.upcoming.UpcomingViewModel

class ViewModelFactory private constructor(private val repository: Repository):
ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(UpcomingViewModel::class.java) -> {
                UpcomingViewModel(repository) as T
            }
            modelClass.isAssignableFrom(FinishedViewModel::class.java) -> {
                FinishedViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                SearchViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}