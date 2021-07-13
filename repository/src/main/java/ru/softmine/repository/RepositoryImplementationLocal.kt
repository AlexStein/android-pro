package ru.softmine.repository

import ru.softmine.model.data.AppState
import ru.softmine.model.data.DataModel
import ru.softmine.repository.interfaces.DataSourceLocal

class RepositoryImplementationLocal(private val dataSource: DataSourceLocal<List<DataModel>>) :
    ru.softmine.repository.interfaces.RepositoryLocal<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }

    override suspend fun saveToDB(appState: AppState) {
        dataSource.saveToDB(appState)
    }

    override suspend fun saveFavorite(word: String, insert: Boolean) {
        dataSource.saveFavorite(word, insert)
    }

    override suspend fun isFavorite(word: String): Boolean {
        return dataSource.isFavorite(word)
    }
}
