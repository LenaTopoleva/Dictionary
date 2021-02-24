package com.lenatopoleva.dictionary.view

import android.app.Application
import com.lenatopoleva.dictionary.di.modules.application
import com.lenatopoleva.dictionary.di.modules.mainActivity
import com.lenatopoleva.dictionary.di.modules.navigation
import com.lenatopoleva.dictionary.di.modules.wordsListScreen
import org.koin.core.context.startKoin

class App: Application() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            modules(listOf(application, navigation, mainActivity, wordsListScreen))
        }
    }

}