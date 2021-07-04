package ru.softmine.translator.model.repository

import ru.softmine.translator.model.data.AppState

interface RepositoryLocal<T> : Repository<T> {
    suspend fun saveToDB(appState: AppState)
}
