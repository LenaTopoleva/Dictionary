package com.lenatopoleva.dictionary.model.datasource

import com.lenatopoleva.dictionary.model.data.AppState

interface DataSourceLocal<T> : DataSource<T> {

    suspend fun saveToDB(appState: AppState)

}
