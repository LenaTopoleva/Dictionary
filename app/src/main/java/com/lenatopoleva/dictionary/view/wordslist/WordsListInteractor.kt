package com.lenatopoleva.dictionary.view.wordslist

import com.lenatopoleva.dictionary.model.data.AppState
import com.lenatopoleva.dictionary.model.data.DataModel
import com.lenatopoleva.dictionary.model.repository.Repository
import com.lenatopoleva.dictionary.presenter.Interactor
import io.reactivex.Observable

class WordsListInteractor(
    private val remoteRepository: Repository<List<DataModel>>,
    private val localRepository: Repository<List<DataModel>>
) : Interactor<AppState> {

    override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> {
        return if (fromRemoteSource) {
            remoteRepository.getData(word).map { AppState.Success(it) }
        } else {
            localRepository.getData(word).map { AppState.Success(it) }
        }
    }
}
