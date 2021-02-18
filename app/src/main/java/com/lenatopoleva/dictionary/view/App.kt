package com.lenatopoleva.dictionary.view

import android.app.Application
import com.lenatopoleva.dictionary.di.AppComponent
import com.lenatopoleva.dictionary.di.modules.AppModule
import com.lenatopoleva.dictionary.di.DaggerAppComponent

class App: Application() {

    companion object {
        lateinit var instance: App
    }

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

}