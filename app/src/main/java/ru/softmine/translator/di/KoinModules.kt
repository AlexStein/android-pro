package ru.softmine.translator.di

import androidx.room.Room
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import ru.softmine.model.data.DataModel
import ru.softmine.repository.room.db.Database
import ru.softmine.repository.RetrofitImplementation
import ru.softmine.repository.RoomDataBaseImplementation
import ru.softmine.repository.interfaces.Repository
import ru.softmine.repository.RepositoryImplementation
import ru.softmine.repository.RepositoryImplementationLocal
import ru.softmine.repository.interfaces.RepositoryLocal
import ru.softmine.translator.view.*


fun injectDependencies() = loadModules

private val loadModules by lazy {
    loadKoinModules(listOf(application, mainScreen, descriptionScreen))
}

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

val descriptionScreen = module {
    factory { ru.softmine.descriptionscreen.DescriptionViewModel(get()) }
    factory { ru.softmine.descriptionscreen.DescriptionInteractor(get()) }
}