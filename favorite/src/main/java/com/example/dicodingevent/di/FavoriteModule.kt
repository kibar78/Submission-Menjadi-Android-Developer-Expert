package com.example.dicodingevent.di

import com.example.dicodingevent.favorite.FavoriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    viewModel{ FavoriteViewModel(get()) }
}