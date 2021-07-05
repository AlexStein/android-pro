package ru.softmine.translator.model.repository

import ru.softmine.translator.model.data.AppState

interface RepositoryLocal<T> : Repository<T> {
    suspend fun saveToDB(appState: AppState)
    suspend fun saveFavorite(word: String, insert: Boolean)
    suspend fun isFavorite(word: String) : Boolean
}
