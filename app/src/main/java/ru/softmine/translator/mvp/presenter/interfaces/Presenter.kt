package ru.softmine.translator.mvp.presenter.interfaces

import ru.softmine.translator.mvp.model.data.AppState
import ru.softmine.translator.mvp.view.interfaces.View

interface Presenter<T : AppState, V : View> {

    fun attachView(view: V)

    fun detachView(view: V)

    fun getData(word: String, isOnline: Boolean)
}
