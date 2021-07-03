package ru.softmine.translator.model.datasource

import io.reactivex.rxjava3.core.Observable
import ru.softmine.translator.model.data.DataModel
import ru.softmine.translator.model.datasource.interfaces.DataSource

class DataSourceLocal(private val remoteProvider: RoomDataBaseImplementation = RoomDataBaseImplementation()) :
    DataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> = remoteProvider.getData(word)
}
