package com.aneke.peter.eyweather.di

import com.aneke.peter.eyweather.main.MainRepository
import com.aneke.peter.eyweather.main.MainViewModel
import com.aneke.peter.eyweather.network.RetrofitClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}

private val repositoryModule = module {
    single { MainRepository(get()) }
}

private val dataModule = module {
    single { RetrofitClient.makeApiService() }
}

val appModules = listOf(viewModelModule, repositoryModule, dataModule)