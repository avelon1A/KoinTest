package com.example.kointest

import com.example.kointest.repo.ProductRepository
import com.example.kointest.viewModel.ProductViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { ProductRepository(get()) }
    viewModel { ProductViewModel(get()) }
}

