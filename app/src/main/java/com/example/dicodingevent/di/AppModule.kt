package com.example.dicodingevent.di

import com.example.dicodingevent.core.domain.usecase.DataStoreInteractor
import com.example.dicodingevent.core.domain.usecase.DataStoreUseCase
import com.example.dicodingevent.core.domain.usecase.RemoteInteractor
import com.example.dicodingevent.core.domain.usecase.RemoteUseCase
import com.example.dicodingevent.core.domain.usecase.RoomInteractor
import com.example.dicodingevent.core.domain.usecase.RoomUseCase
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
    factory<RemoteUseCase>{ RemoteInteractor(get()) }
    factory<RoomUseCase>{ RoomInteractor(get()) }
    factory<DataStoreUseCase>{ DataStoreInteractor(get()) }
}
val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get(), get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { FinishedViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { UpcomingViewModel(get()) }
}