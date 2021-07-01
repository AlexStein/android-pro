package ru.softmine.translator.mvp.model.datasource

import ru.softmine.translator.mvp.model.data.DataModel
import io.reactivex.rxjava3.core.Observable
import ru.softmine.translator.mvp.model.datasource.interfaces.DataSource

class RoomDataBaseImplementation : DataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
