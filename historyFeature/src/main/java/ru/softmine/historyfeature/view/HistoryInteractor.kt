package ru.softmine.historyfeature.view

import ru.softmine.model.data.AppState
import ru.softmine.model.data.DataModel
import ru.softmine.repository.interfaces.Repository
import ru.softmine.repository.interfaces.RepositoryLocal
import ru.softmine.core.interfaces.Interactor

class HistoryInteractor(
    private val repositoryRemote: Repository<List<DataModel>>,
    private val repositoryLocal: RepositoryLocal<List<DataModel>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return AppState.Success(repositoryLocal.getData(word))
    }
}
