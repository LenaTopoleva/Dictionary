package com.lenatopoleva.dictionary.view.wordslist

import com.lenatopoleva.dictionary.model.data.AppState
import com.lenatopoleva.dictionary.model.data.DataModel
import com.lenatopoleva.dictionary.model.datasource.DataSourceLocal
import com.lenatopoleva.dictionary.model.datasource.DataSourceRemote
import com.lenatopoleva.dictionary.model.repository.RepositoryImpl
import com.lenatopoleva.dictionary.presenter.FragmentPresenter
import com.lenatopoleva.dictionary.rx.SchedulerProvider
import com.lenatopoleva.dictionary.view.App
import com.lenatopoleva.dictionary.view.base.View
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import ru.terrakok.cicerone.Router

class WordsListPresenterImpl <T : AppState, V : View>(
    private val interactor: WordsListInteractor = WordsListInteractor(
        RepositoryImpl(DataSourceRemote()),
        RepositoryImpl(DataSourceLocal())
    ),
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    protected val schedulerProvider: SchedulerProvider = SchedulerProvider(),
    private val router: Router = App.instance.router
) : FragmentPresenter<T, V> {

    private var currentView: V? = null

    private var rvData: List<DataModel>? = null

    override fun attachView(view: V) {
        if (view != currentView) {
            currentView = view
            rvData?.let { currentView?.renderData(AppState.Success(it))}
        }
    }

    override fun detachView(view: V) {
        compositeDisposable.clear()
        if (view == currentView) {
            currentView = null
        }
    }

    override fun getData(word: String, isOnline: Boolean) {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe { currentView?.renderData(AppState.Loading(null)) }
                .subscribeWith(getObserver())
        )
    }

    private fun getObserver(): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>() {

            override fun onNext(appState: AppState) {
                currentView?.renderData(appState)
            }

            override fun onError(e: Throwable) {
                currentView?.renderData(AppState.Error(e))
            }

            override fun onComplete() {
            }
        }
    }

    override fun backClick(): Boolean {
        router.exit()
        return true
    }

    override fun dataObtained(dataModel: List<DataModel>) {
        rvData = dataModel
    }


}