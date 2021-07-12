package ru.softmine.repository.interfaces

interface Repository<T> {
    suspend fun getData(word: String): T
}