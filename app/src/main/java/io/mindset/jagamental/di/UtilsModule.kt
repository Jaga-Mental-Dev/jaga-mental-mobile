package io.mindset.jagamental.di

import io.mindset.jagamental.utils.HelperComponent
import org.koin.dsl.module

val utilsModule = module {
    single { HelperComponent() }
}
