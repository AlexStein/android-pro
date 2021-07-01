package ru.softmine.translator.mvp.model.datasource

import ru.softmine.translator.mvp.model.data.DataModel
import io.reactivex.rxjava3.core.Observable
import ru.softmine.translator.mvp.model.datasource.interfaces.DataSource

class DataSourceRemote(private val remoteProvider: RetrofitImplementation = RetrofitImplementation()) :
    DataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> = remoteProvider.getData(word)
}
