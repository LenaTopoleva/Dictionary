package com.lenatopoleva.dictionary.view.main

import com.lenatopoleva.dictionary.navigation.Screens
import com.lenatopoleva.dictionary.presenter.MainActivityPresenter
import com.lenatopoleva.dictionary.view.App
import com.lenatopoleva.dictionary.view.base.View
import ru.terrakok.cicerone.Router

class MainPresenterImpl<V: View>( private val router: Router = App.instance.router): MainActivityPresenter<V> {

    override fun backClick() {
        router.exit()
    }

    override fun onCreate() {
        router.replaceScreen(Screens.WordsListScreen())
    }

    private var currentView: V? = null

    override fun attachView(view: V) {
        if (view != currentView) {
            currentView = view
        }
    }

    override fun detachView(view: V) {
        if (view == currentView) {
            currentView = null
        }
    }
}