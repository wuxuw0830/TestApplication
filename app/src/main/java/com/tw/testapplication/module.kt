package com.tw.testapplication

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {
    single {
        MainViewModel()
    }
}
val appModule = module {
    viewModel { MainViewModel() }
}