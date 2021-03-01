package com.lenatopoleva.dictionary.model.repository

import com.lenatopoleva.dictionary.model.data.AppState
import com.lenatopoleva.dictionary.model.data.DataModel
import com.lenatopoleva.dictionary.model.datasource.DataSourceLocal

class RepositoryLocalImpl(private val dataSource: DataSourceLocal<List<DataModel>>) :
    RepositoryLocal<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }

    override suspend fun saveToDB(appState: AppState) {
        dataSource.saveToDB(appState)
    }
}