package com.example.dicodingevent.di

import com.example.dicodingevent.core.domain.usecase.EventsInteractor
import com.example.dicodingevent.core.domain.usecase.EventsUseCase
import com.example.dicodingevent.ui.detail.DetailViewModel
import com.example.dicodingevent.ui.favorite.FavoriteViewModel
import com.example.dicodingevent.ui.finished.FinishedViewModel
import com.example.dicodingevent.ui.home.HomeViewModel
import com.example.dicodingevent.ui.search.SearchViewModel
import com.example.dicodingevent.ui.settings.SettingsViewModel
import com.example.dicodingevent.ui.upcoming.UpcomingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<EventsUseCase>{ EventsInteractor(get()) }
}
val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { FinishedViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { UpcomingViewModel(get()) }
}