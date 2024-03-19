package com


import com.example.kointest.appModule
import com.example.kointest.retrofitModule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

fun initDI() {
    startKoin {
        val appSetupModule = module {
            single {
                modules(
                    listOf(appModule,retrofitModule)
                    )
            }
        }
    }
}


fun deinitDI() {
    stopKoin()
}
