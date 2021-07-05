package ru.softmine.translator.view

import ru.softmine.translator.model.data.AppState
import ru.softmine.translator.model.data.DataModel
import ru.softmine.translator.model.repository.Repository
import ru.softmine.translator.model.repository.RepositoryLocal
import ru.softmine.translator.view.interfaces.Interactor

class HistoryInteractor(
    private val repositoryRemote: Repository<List<DataModel>>,
    private val repositoryLocal: RepositoryLocal<List<DataModel>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return AppState.Success(repositoryLocal.getData(word))
    }
}
