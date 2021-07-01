package ru.softmine.translator.mvp.model.datasource

import ru.softmine.translator.mvp.model.data.DataModel
import io.reactivex.rxjava3.core.Observable
import ru.softmine.translator.mvp.model.datasource.interfaces.DataSource

class DataSourceLocal(private val remoteProvider: RoomDataBaseImplementation = RoomDataBaseImplementation()) :
    DataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> = remoteProvider.getData(word)
}
