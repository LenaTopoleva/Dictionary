package com.lenatopoleva.dictionary.view

import android.app.Application
import com.lenatopoleva.dictionary.di.modules.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidContext(applicationContext)
        }
    }

}