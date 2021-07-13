package ru.softmine.repository.interfaces

interface RepositoryLocal<T> : Repository<T> {
    suspend fun saveToDB(appState: ru.softmine.model.data.AppState)
    suspend fun saveFavorite(word: String, insert: Boolean)
    suspend fun isFavorite(word: String) : Boolean
}