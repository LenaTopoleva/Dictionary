package com.lenatopoleva.dictionary.view.wordslist

import com.lenatopoleva.dictionary.model.data.AppState
import com.lenatopoleva.dictionary.model.data.DataModel
import com.lenatopoleva.dictionary.model.repository.Repository
import com.lenatopoleva.dictionary.viewmodel.Interactor

class WordsListInteractor(
    private val remoteRepository: Repository<List<DataModel>>,
    private val localRepository: Repository<List<DataModel>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return if (fromRemoteSource) {
             AppState.Success(remoteRepository.getData(word))
        } else {
           AppState.Success( localRepository.getData(word))
        }
    }
}
