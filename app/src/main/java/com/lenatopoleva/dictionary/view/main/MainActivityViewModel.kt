package com.lenatopoleva.dictionary.view.main

import androidx.lifecycle.ViewModel
import com.lenatopoleva.dictionary.navigation.Screens
import ru.terrakok.cicerone.Router

class MainActivityViewModel (private val router: Router ): ViewModel(){

    fun backPressed() {
        router.exit()
    }

    fun onCreate() {
        router.replaceScreen(Screens.WordsListScreen())
    }

    fun historyMenuItemClicked() {
        router.navigateTo(Screens.HistoryScreen())
    }
}