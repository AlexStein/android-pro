package ru.softmine.translator.model.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = arrayOf("word"), unique = true)])
class HistoryEntity(
    @field:PrimaryKey
    @field:ColumnInfo(name = "word")
    var word: String,
    @field:ColumnInfo(name = "description")
    var description: String?,
    @field:ColumnInfo(name = "created_at", defaultValue = "CURRENT_TIMESTAMP")
    var created: String,
    @field:ColumnInfo(name = "searches_count", defaultValue = "1")
    var count: Int
)
