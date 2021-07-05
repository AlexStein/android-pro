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
import ru.softmine.translator.view.*


val application = module {
    single { Room.databaseBuilder(get(), Database::class.java, "Database")
        .fallbackToDestructiveMigration()
        .build()
    }
    single { get<Database>().historyDao() }
    single { get<Database>().favoriteDao() }
    single<Repository<List<DataModel>>> { RepositoryImplementation(RetrofitImplementation()) }
    single<RepositoryLocal<List<DataModel>>> {
        RepositoryImplementationLocal(RoomDataBaseImplementation(get(), get()))
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

val descriptionScreen = module {
    factory { DescriptionViewModel(get()) }
    factory { DescriptionInteractor(get()) }
}