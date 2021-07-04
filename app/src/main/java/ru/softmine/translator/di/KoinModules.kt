package ru.softmine.translator.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.softmine.translator.model.data.DataModel
import ru.softmine.translator.model.datasource.RetrofitImplementation
import ru.softmine.translator.model.datasource.RoomDataBaseImplementation
import ru.softmine.translator.model.repository.Repository
import ru.softmine.translator.model.repository.RepositoryImplementation
import ru.softmine.translator.view.MainInteractor
import ru.softmine.translator.view.MainViewModel

const val NAME_REMOTE = "Remote"
const val NAME_LOCAL = "Local"


val application = module {
    single<Repository<List<DataModel>>>(named(NAME_REMOTE)) { RepositoryImplementation(
        RetrofitImplementation()
    ) }
    single<Repository<List<DataModel>>>(named(NAME_LOCAL)) { RepositoryImplementation(
        RoomDataBaseImplementation()
    ) }
}

val mainScreen = module {
    factory { MainInteractor(get(named(NAME_REMOTE)), get(named(NAME_LOCAL))) }
    factory { MainViewModel(get()) }
}