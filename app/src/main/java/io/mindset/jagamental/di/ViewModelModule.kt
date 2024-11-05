package io.mindset.jagamental.di

import io.mindset.jagamental.ui.MainViewModel
import io.mindset.jagamental.ui.screen.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { LoginViewModel(get()) }
}