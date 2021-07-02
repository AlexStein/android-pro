package ru.softmine.translator.view

import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import javax.inject.Named
import ru.softmine.translator.di.module.NAME_LOCAL
import ru.softmine.translator.di.module.NAME_REMOTE
import ru.softmine.translator.model.data.AppState
import ru.softmine.translator.model.data.DataModel
import ru.softmine.translator.model.repository.Repository
import ru.softmine.translator.view.interfaces.Interactor


class MainInteractor @Inject constructor(
    @Named(NAME_REMOTE) val repositoryRemote: Repository<List<DataModel>>,
    @Named(NAME_LOCAL) val repositoryLocal: Repository<List<DataModel>>
) : Interactor<AppState> {

    override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> {
        return if (fromRemoteSource) {
            repositoryRemote
        } else {
            repositoryLocal
        }.getData(word).map { AppState.Success(it) }
    }
}
