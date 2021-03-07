package com.lenatopoleva.dictionary.model.repository

import com.lenatopoleva.dictionary.model.data.AppState

interface RepositoryLocal<T> : Repository<T> {

    suspend fun saveToDB(appState: AppState)

}