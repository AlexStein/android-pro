package ru.softmine.translator.di

import androidx.room.Room
import org.koin.dsl.module
import ru.softmine.translator.model.data.DataModel
import ru.softmine.translator.model.data.room.db.Database
import ru.softmine.translator.model.datasource.RetrofitImplementation
import ru.softmine.translator.model.datasource.RoomDataBaseImplementation
import ru.softmine.translator.model.repository.Repository
import ru.softmine.translator.model.repository.RepositoryImplementation
import ru.softmine.translator.model.repository.RepositoryImplementationLocal
import ru.softmine.translator.model.repository.RepositoryLocal
import ru.softmine.translator.view.HistoryInteractor
import ru.softmine.translator.view.HistoryViewModel
import ru.softmine.translator.view.MainInteractor
import ru.softmine.translator.view.MainViewModel


val application = module {
    single { Room.databaseBuilder(get(), Database::class.java, "Database").build() }
    single { get<Database>().historyDao() }
    single<Repository<List<DataModel>>> { RepositoryImplementation(RetrofitImplementation()) }
    single<RepositoryLocal<List<DataModel>>> {
        RepositoryImplementationLocal(RoomDataBaseImplementation(get()))
    }
}

val mainScreen = module {
    factory { MainViewModel(get()) }
    factory { MainInteractor(get(), get()) }
}

val historyScreen = module {
    factory { HistoryViewModel(get()) }
    factory { HistoryInteractor(get(), get()) }
}