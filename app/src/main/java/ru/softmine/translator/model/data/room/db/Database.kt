package ru.softmine.translator.model.data.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.softmine.translator.model.data.room.HistoryEntity
import ru.softmine.translator.model.data.room.dao.HistoryDao

@Database(entities = [HistoryEntity::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract fun historyDao(): HistoryDao
}
