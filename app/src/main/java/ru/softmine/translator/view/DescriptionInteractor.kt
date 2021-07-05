package ru.softmine.translator.view

import ru.softmine.translator.model.data.DataModel
import ru.softmine.translator.model.repository.RepositoryLocal

class DescriptionInteractor(private val repositoryLocal: RepositoryLocal<List<DataModel>>) {
    suspend fun saveFavorite(word: String, insert: Boolean) {
        repositoryLocal.saveFavorite(word, insert)
    }

    suspend fun isFavorite(word: String) : Boolean {
        return repositoryLocal.isFavorite(word)
    }
}