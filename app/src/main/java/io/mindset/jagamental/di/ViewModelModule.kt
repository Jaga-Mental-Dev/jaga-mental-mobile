package io.mindset.jagamental.di

import io.mindset.jagamental.ui.MainViewModel
import io.mindset.jagamental.ui.screen.chatbot.ChatViewModel
import io.mindset.jagamental.ui.screen.dashboard.DashboardViewModel
import io.mindset.jagamental.ui.screen.journal.JournalViewModel
import io.mindset.jagamental.ui.screen.journal.add.capture.CaptureViewModel
import io.mindset.jagamental.ui.screen.journal.add.input.InputJournalViewModel
import io.mindset.jagamental.ui.screen.journal.add.photoresult.PhotoResultViewModel
import io.mindset.jagamental.ui.screen.journal.add.preview.ResultPreviewViewModel
import io.mindset.jagamental.ui.screen.journal.result.JournalResultViewModel
import io.mindset.jagamental.ui.screen.login.LoginViewModel
import io.mindset.jagamental.ui.screen.profile.ProfileViewModel
import io.mindset.jagamental.ui.screen.register.RegisterViewModel
import io.mindset.jagamental.ui.screen.root.RootViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get(), get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { DashboardViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { RootViewModel() }
    viewModel { ProfileViewModel(get()) }
    viewModel { CaptureViewModel() }
    viewModel { PhotoResultViewModel(get(), get()) }
    viewModel { ResultPreviewViewModel(get()) }
    viewModel { JournalViewModel(get()) }
    viewModel { InputJournalViewModel(get(), get()) }
    viewModel { JournalResultViewModel(get(), get()) }
    viewModel { ChatViewModel(get()) }
}