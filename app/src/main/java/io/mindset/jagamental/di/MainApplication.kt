package io.mindset.jagamental.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        GlobalContext.startKoin {
            androidContext(this@MainApplication)
            modules(
                networkModule,
                viewModelModule,
                repositoryModule,
                sharedPreferencesModule,
                utilsModule,
            )
        }
    }
}

