package com.lenatopoleva.dictionary.view.main

import androidx.lifecycle.ViewModel
import com.lenatopoleva.dictionary.navigation.Screens
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val router: Router ): ViewModel(){

    fun backPressed() {
        router.exit()
    }

    fun onCreate() {
        router.replaceScreen(Screens.WordsListScreen())
    }
}