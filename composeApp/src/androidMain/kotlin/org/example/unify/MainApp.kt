package org.example.unify

import android.app.Application
import org.example.unify.di.initKoin
import org.koin.android.ext.koin.androidContext

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MainApp)
        }
    }
}