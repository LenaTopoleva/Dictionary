package com.lenatopoleva.dictionary.model.datasource

import com.lenatopoleva.dictionary.model.data.DataModel

class DataSourceLocal(private val remoteProvider: RoomDataBaseImpl = RoomDataBaseImpl()) :
    DataSource<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> = remoteProvider.getData(word)
}
