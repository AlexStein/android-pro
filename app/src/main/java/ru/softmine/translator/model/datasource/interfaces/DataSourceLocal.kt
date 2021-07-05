package ru.softmine.translator.model.datasource.interfaces

import ru.softmine.translator.model.data.AppState

interface DataSourceLocal<T> : DataSource<T> {
    suspend fun saveToDB(appState: AppState)
    suspend fun saveFavorite(word: String, insert: Boolean)
    suspend fun isFavorite(word: String) : Boolean
}
