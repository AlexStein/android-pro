package ru.softmine.repository

import ru.softmine.model.data.AppState
import ru.softmine.model.data.DataModel
import ru.softmine.repository.room.FavoriteEntity
import ru.softmine.repository.room.dao.FavoriteDao
import ru.softmine.repository.room.dao.HistoryDao
import ru.softmine.repository.interfaces.DataSourceLocal

class RoomDataBaseImplementation(
    private val historyDao: HistoryDao,
    private val favoriteDao: FavoriteDao
) : DataSourceLocal<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return mapHistoryEntityToSearchResult(historyDao.all())
    }

    override suspend fun saveToDB(appState: AppState) {
        convertDataModelSuccessToEntity(appState)?.let {
            if (historyDao.presentInHistory(it.word) == 0) {
                historyDao.insert(it)
            } else {
                historyDao.updateCounter(it.word)
            }
        }
    }

    override suspend fun saveFavorite(word: String, insert: Boolean) {
        val fav = FavoriteEntity(word = word, description = null)
        if (insert) {
            favoriteDao.insert(fav)
        } else {
            favoriteDao.delete(fav)
        }
    }

    override suspend fun isFavorite(word: String) : Boolean {
        return favoriteDao.getCount(word) > 0
    }
}
