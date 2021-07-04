package ru.softmine.translator.model.datasource.interfaces

interface DataSource<T> {
    suspend fun getData(word: String): T
}
