package ru.softmine.repository.interfaces

interface DataSource<T> {
    suspend fun getData(word: String): T
}
