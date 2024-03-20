package com.example.app

import android.app.Application
import com.example.kointest.appModule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class TestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {}

    }
    override fun onTerminate() {
        stopKoin()
        super.onTerminate()
    }
}
