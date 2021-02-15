package com.lenatopoleva.dictionary.presenter

import com.lenatopoleva.dictionary.view.base.View

interface MainActivityPresenter<V: View> {

    fun attachView(view: V)

    fun detachView(view: V)

    fun onCreate()

    fun backClick()
}