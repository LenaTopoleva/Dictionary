package com.lenatopoleva.dictionary.model.datasource

import com.lenatopoleva.dictionary.model.data.DataModel
import io.reactivex.Observable

class DataSourceLocal(private val remoteProvider: RoomDataBaseImpl = RoomDataBaseImpl()) :
    DataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> = remoteProvider.getData(word)
}
