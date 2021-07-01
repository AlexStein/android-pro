package ru.softmine.translator.mvp.presenter

import io.reactivex.rxjava3.core.Observable

import ru.softmine.translator.mvp.model.data.AppState
import ru.softmine.translator.mvp.model.data.DataModel
import ru.softmine.translator.mvp.model.repository.Repository
import ru.softmine.translator.mvp.presenter.interfaces.Interactor

class MainInteractor(
    private val remoteRepository: Repository<List<DataModel>>,
    private val localRepository: Repository<List<DataModel>>
) : Interactor<AppState> {

    override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> {
        return if (fromRemoteSource) {
            remoteRepository.getData(word).map {it -> AppState.Success(it) }
        } else {
            localRepository.getData(word).map {it -> AppState.Success(it) }
        }
    }
}
