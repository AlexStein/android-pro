package ru.softmine.translator.model.data.room.dao

import androidx.room.*
import ru.softmine.translator.model.data.room.FavoriteEntity

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM FavoriteEntity")
    suspend fun all(): List<FavoriteEntity>

    @Query("SELECT * FROM FavoriteEntity WHERE word LIKE :word")
    suspend fun getDataByWord(word: String): FavoriteEntity

    @Query("SELECT Count(*) FROM FavoriteEntity WHERE word LIKE :word")
    suspend fun getCount(word: String): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: FavoriteEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(entities: List<FavoriteEntity>)

    @Update
    suspend fun update(entity: FavoriteEntity)

    @Delete
    suspend fun delete(entity: FavoriteEntity)
}
