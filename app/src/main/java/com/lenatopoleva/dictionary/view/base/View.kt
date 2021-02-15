package com.lenatopoleva.dictionary.view.base

import com.lenatopoleva.dictionary.model.data.AppState

interface View {

    fun renderData(appState: AppState)

}