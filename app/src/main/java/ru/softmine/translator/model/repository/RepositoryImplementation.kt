package ru.softmine.translator.model.repository

import io.reactivex.rxjava3.core.Observable
import ru.softmine.translator.model.data.DataModel
import ru.softmine.translator.model.datasource.interfaces.DataSource

class RepositoryImplementation(private val dataSource: DataSource<List<DataModel>>) :
    Repository<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> {
        return dataSource.getData(word)
    }
}
