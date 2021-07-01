package ru.softmine.translator.mvp.view.interfaces

import ru.softmine.translator.mvp.model.data.AppState

interface View {
    fun renderData(appState: AppState)
}
