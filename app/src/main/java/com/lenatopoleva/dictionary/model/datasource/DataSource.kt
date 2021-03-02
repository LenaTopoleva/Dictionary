package com.lenatopoleva.dictionary.model.datasource

import com.lenatopoleva.dictionary.model.data.DataModel


interface DataSource<T> {

    suspend fun getData(word: String): List<DataModel>?
}
