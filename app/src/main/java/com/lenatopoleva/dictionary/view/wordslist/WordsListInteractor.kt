package com.lenatopoleva.dictionary.view.wordslist

import com.lenatopoleva.dictionary.model.data.AppState
import com.lenatopoleva.dictionary.model.data.DataModel
import com.lenatopoleva.dictionary.model.repository.Repository
import com.lenatopoleva.dictionary.model.repository.RepositoryLocal
import com.lenatopoleva.dictionary.viewmodel.Interactor

class WordsListInteractor(
    private val remoteRepository: Repository<List<DataModel>>,
    private val localRepository: RepositoryLocal<List<DataModel>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        val appState: AppState
        if (fromRemoteSource) {
            appState = AppState.Success(remoteRepository.getData(word))
            localRepository.saveToDB(appState)
        } else {
           appState = AppState.Success( localRepository.getData(word))
        }
        return appState
    }
}
