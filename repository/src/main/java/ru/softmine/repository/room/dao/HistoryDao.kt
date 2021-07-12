package ru.softmine.repository.room.dao

import androidx.room.*
import ru.softmine.repository.room.HistoryEntity

@Dao
interface HistoryDao {

    @Query("SELECT * FROM HistoryEntity ORDER BY searches_count DESC, created_at DESC")
    suspend fun all(): List<HistoryEntity>

    @Query("SELECT * FROM HistoryEntity WHERE word LIKE :word")
    suspend fun getDataByWord(word: String): HistoryEntity

    @Query("SELECT Count(*) FROM HistoryEntity WHERE word LIKE :word")
    suspend fun presentInHistory(word: String): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: HistoryEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(entities: List<HistoryEntity>)

    @Query("UPDATE HistoryEntity SET searches_count = searches_count + 1 WHERE word LIKE :word")
    suspend fun updateCounter(word: String)

    @Update
    suspend fun update(entity: HistoryEntity)

    @Delete
    suspend fun delete(entity: HistoryEntity)
}
