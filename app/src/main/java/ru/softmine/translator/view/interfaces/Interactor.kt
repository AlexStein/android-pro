package ru.softmine.translator.view.interfaces

interface Interactor<T> {
    suspend fun getData(word: String, fromRemoteSource: Boolean): T
}