package com.lenatopoleva.dictionary.presenter

import com.lenatopoleva.dictionary.model.data.AppState
import com.lenatopoleva.dictionary.model.data.DataModel
import com.lenatopoleva.dictionary.view.base.View

interface FragmentPresenter<T : AppState, V : View> {

    fun attachView(view: V)

    fun detachView(view: V)

    fun getData(word: String, isOnline: Boolean)

    fun backClick(): Boolean

    fun dataObtained(dataModel: List<DataModel>)

}