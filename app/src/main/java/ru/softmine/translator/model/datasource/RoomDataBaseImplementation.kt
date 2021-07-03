package ru.softmine.translator.model.datasource

import io.reactivex.rxjava3.core.Observable
import ru.softmine.translator.model.data.DataModel
import ru.softmine.translator.model.datasource.interfaces.DataSource

class RoomDataBaseImplementation : DataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
