package ru.softmine.repository.interfaces

import ru.softmine.model.data.AppState

interface DataSourceLocal<T> : DataSource<T> {
    suspend fun saveToDB(appState: AppState)
    suspend fun saveFavorite(word: String, insert: Boolean)
    suspend fun isFavorite(word: String) : Boolean
}
