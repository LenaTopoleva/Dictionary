package com.lenatopoleva.dictionary.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lenatopoleva.dictionary.R
import com.lenatopoleva.dictionary.model.data.AppState
import com.lenatopoleva.dictionary.presenter.MainActivityPresenter
import com.lenatopoleva.dictionary.view.App
import com.lenatopoleva.dictionary.view.BackButtonListener
import com.lenatopoleva.dictionary.view.base.View
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class MainActivity : AppCompatActivity(), View {

    val navigator = SupportAppNavigator(this, supportFragmentManager, R.id.container)

    lateinit var presenter: MainActivityPresenter<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = App.instance.presenterHolder.getMainPresenter(App.instance.router)
        presenter.onCreate()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        App.instance.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        App.instance.navigatorHolder.removeNavigator()
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView(this)
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is BackButtonListener && it.backPressed()) {
                return
            }
        }
        presenter.backClick()
    }

    override fun onDestroy() {
        if (isFinishing)
            App.instance.presenterHolder.clearMainPresenter()
        super.onDestroy()
    }

    override fun renderData(appState: AppState) {
//        TODO("Not yet implemented")
    }
}