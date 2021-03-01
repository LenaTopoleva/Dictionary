package com.lenatopoleva.dictionary.model.repository

import com.lenatopoleva.dictionary.model.data.DataModel


interface Repository<T> {

    suspend fun getData(word: String): List<DataModel>?
}
