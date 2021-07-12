package ru.softmine.descriptionscreen

import ru.softmine.model.data.DataModel
import ru.softmine.repository.interfaces.RepositoryLocal

class DescriptionInteractor(private val repositoryLocal: RepositoryLocal<List<DataModel>>) {
    suspend fun saveFavorite(word: String, insert: Boolean) {
        repositoryLocal.saveFavorite(word, insert)
    }

    suspend fun isFavorite(word: String) : Boolean {
        return repositoryLocal.isFavorite(word)
    }
}