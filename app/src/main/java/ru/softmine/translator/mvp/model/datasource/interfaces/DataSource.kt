package ru.softmine.translator.mvp.model.datasource.interfaces

import io.reactivex.rxjava3.core.Observable

interface DataSource<T> {

    fun getData(word: String): Observable<T>
}
