package ru.softmine.translator.view

import ru.softmine.model.data.AppState
import ru.softmine.model.data.DataModel
import ru.softmine.repository.interfaces.Repository
import ru.softmine.repository.interfaces.RepositoryLocal
import ru.softmine.core.interfaces.Interactor

class MainInteractor(
    private val repositoryRemote: Repository<List<DataModel>>,
    private val repositoryLocal: RepositoryLocal<List<DataModel>>
) : Interactor<AppState> {


    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        val appState: AppState
        if (fromRemoteSource) {
            appState = AppState.Success(repositoryRemote.getData(word))
            repositoryLocal.saveToDB(appState)
        } else {
            appState = AppState.Success(repositoryLocal.getData(word))
        }
        return appState
    }
}
