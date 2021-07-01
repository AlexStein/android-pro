package ru.softmine.translator.mvp.model.repository

import ru.softmine.translator.mvp.model.data.DataModel
import ru.softmine.translator.mvp.model.datasource.interfaces.DataSource
import io.reactivex.rxjava3.core.Observable

class RepositoryImplementation(private val dataSource: DataSource<List<DataModel>>) :
    Repository<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> {
        return dataSource.getData(word)
    }
}
