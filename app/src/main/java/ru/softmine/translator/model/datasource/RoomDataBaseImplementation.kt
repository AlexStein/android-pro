package ru.softmine.translator.model.datasource

import ru.softmine.translator.model.data.AppState
import ru.softmine.translator.model.data.DataModel
import ru.softmine.translator.model.data.room.FavoriteEntity
import ru.softmine.translator.model.data.room.dao.FavoriteDao
import ru.softmine.translator.model.data.room.dao.HistoryDao
import ru.softmine.translator.model.datasource.interfaces.DataSourceLocal
import ru.softmine.translator.utils.convertDataModelSuccessToEntity
import ru.softmine.translator.utils.mapHistoryEntityToSearchResult

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
