package com.lenatopoleva.dictionary.view.main

import androidx.lifecycle.ViewModel
import com.lenatopoleva.dictionary.navigation.Screens
import com.lenatopoleva.dictionary.view.App
import ru.terrakok.cicerone.Router

class MainActivityViewModel (private val router: Router = App.instance.router): ViewModel(){

    fun backPressed() {
        router.exit()
    }

    fun onCreate() {
        router.replaceScreen(Screens.WordsListScreen())
    }
}