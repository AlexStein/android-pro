package ru.softmine.translator.view

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class DescriptionViewModel(private val interactor: DescriptionInteractor) : ViewModel() {

    private val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
    )

    private fun cancelJob() {
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

    fun saveFavorite(word: String, insert: Boolean) {
        cancelJob()
        viewModelCoroutineScope.launch { interactor.saveFavorite(word, insert) }
    }

    fun isFavorite(word: String): Boolean {
        cancelJob()
        return runBlocking { interactor.isFavorite(word) }
    }
}
