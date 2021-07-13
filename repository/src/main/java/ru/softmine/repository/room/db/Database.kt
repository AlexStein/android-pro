package ru.softmine.repository.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.softmine.repository.room.FavoriteEntity
import ru.softmine.repository.room.HistoryEntity
import ru.softmine.repository.room.dao.FavoriteDao
import ru.softmine.repository.room.dao.HistoryDao

@Database(entities = [
        HistoryEntity::class,
        FavoriteEntity::class,
    ], 
    version = 1, 
    exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract fun historyDao(): HistoryDao
    abstract fun favoriteDao(): FavoriteDao
}
