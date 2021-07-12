package ru.softmine.repository.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = arrayOf("word"), unique = true)])
class FavoriteEntity(
    @field:PrimaryKey
    @field:ColumnInfo(name = "word") var word: String,
    @field:ColumnInfo(name = "annotation") var description: String?
)
