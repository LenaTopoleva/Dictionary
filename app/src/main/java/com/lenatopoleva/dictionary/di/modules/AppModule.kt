package com.lenatopoleva.dictionary.di.modules

import com.lenatopoleva.dictionary.view.App
import dagger.Module
import dagger.Provides


@Module
class AppModule(val app: App) {

    @Provides
    fun app(): App{
        return app
    }

}