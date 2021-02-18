package com.lenatopoleva.dictionary.view

import android.app.Application
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

class App: Application() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    private val cicerone: Cicerone<Router> = Cicerone.create()

    val  navigatorHolder = cicerone.navigatorHolder

    val  router = cicerone.router


}