package ru.softmine.repository

import ru.softmine.model.data.DataModel
import ru.softmine.repository.interfaces.DataSource

class RepositoryImplementation(private val dataSource: DataSource<List<DataModel>>) :
    ru.softmine.repository.interfaces.Repository<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }
}
