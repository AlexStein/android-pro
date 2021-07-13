package ru.softmine.core.interfaces

interface Interactor<T> {
    suspend fun getData(word: String, fromRemoteSource: Boolean): T
}