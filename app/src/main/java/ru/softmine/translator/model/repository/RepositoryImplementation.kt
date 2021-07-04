package ru.softmine.translator.model.repository

import ru.softmine.translator.model.data.DataModel
import ru.softmine.translator.model.datasource.interfaces.DataSource

class RepositoryImplementation(private val dataSource: DataSource<List<DataModel>>) :
    Repository<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }
}
