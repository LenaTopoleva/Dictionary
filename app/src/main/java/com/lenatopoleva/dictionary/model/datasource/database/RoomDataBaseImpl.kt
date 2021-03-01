package com.lenatopoleva.dictionary.model.datasource.database

import com.lenatopoleva.dictionary.model.data.AppState
import com.lenatopoleva.dictionary.model.data.DataModel
import com.lenatopoleva.dictionary.model.datasource.DataSourceLocal
import com.lenatopoleva.dictionary.model.datasource.database.room.HistoryDao
import com.lenatopoleva.dictionary.utils.convertDataModelSuccessToEntity
import com.lenatopoleva.dictionary.utils.mapHistoryEntityToSearchResult

class RoomDataBaseImpl(private val historyDao: HistoryDao) :
    DataSourceLocal<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return mapHistoryEntityToSearchResult(historyDao.all())
    }

    override suspend fun saveToDB(appState: AppState) {
        convertDataModelSuccessToEntity(appState)?.let {
            historyDao.insert(it)
        }
    }
}