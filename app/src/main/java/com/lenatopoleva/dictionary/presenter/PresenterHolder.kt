package com.lenatopoleva.dictionary.presenter

import com.lenatopoleva.dictionary.model.data.AppState
import com.lenatopoleva.dictionary.view.base.View
import com.lenatopoleva.dictionary.view.main.MainPresenterImpl
import com.lenatopoleva.dictionary.view.wordslist.WordsListPresenterImpl
import ru.terrakok.cicerone.Router

class PresenterHolder {
    companion object{
        private var mainPresenter: MainActivityPresenter<View>? = null
        private var wordsListPresenter: FragmentPresenter<AppState, View>? = null
    }

    fun getMainPresenter(router: Router):  MainActivityPresenter<View> {
        if (mainPresenter == null){
            mainPresenter = MainPresenterImpl<View>(router)
        }
        return mainPresenter as MainActivityPresenter<View>
    }

    fun clearMainPresenter(){
        mainPresenter = null
    }

    fun getWordsListPresenter():  FragmentPresenter<AppState, View> {
        if (wordsListPresenter == null){
            wordsListPresenter = WordsListPresenterImpl<AppState, View>()
        }
        return wordsListPresenter as FragmentPresenter<AppState, View>
    }

    fun clearWordsListPresenter(){
        wordsListPresenter = null
    }
}