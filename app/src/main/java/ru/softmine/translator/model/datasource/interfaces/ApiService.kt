package ru.softmine.translator.model.datasource.interfaces

import ru.softmine.translator.model.data.DataModel
import io.reactivex.rxjava3.core.Observable

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("words/search")
    fun search(@Query("search") wordToSearch: String): Observable<List<DataModel>>
}
