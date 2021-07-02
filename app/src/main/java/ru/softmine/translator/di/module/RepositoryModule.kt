package ru.softmine.translator.di.module

import dagger.Module
import dagger.Provides

import ru.softmine.translator.model.data.DataModel
import ru.softmine.translator.model.datasource.interfaces.DataSource
import ru.softmine.translator.model.datasource.RetrofitImplementation
import ru.softmine.translator.model.datasource.RoomDataBaseImplementation
import ru.softmine.translator.model.repository.Repository
import ru.softmine.translator.model.repository.RepositoryImplementation

import javax.inject.Named
import javax.inject.Singleton

const val NAME_REMOTE = "Remote"
const val NAME_LOCAL = "Local"

@Module
class RepositoryModule {

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideRepositoryRemote(@Named(NAME_REMOTE) dataSourceRemote: DataSource<List<DataModel>>): Repository<List<DataModel>> =
        RepositoryImplementation(dataSourceRemote)

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideRepositoryLocal(@Named(NAME_LOCAL) dataSourceLocal: DataSource<List<DataModel>>): Repository<List<DataModel>> =
        RepositoryImplementation(dataSourceLocal)

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideDataSourceRemote(): DataSource<List<DataModel>> =
        RetrofitImplementation()

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideDataSourceLocal(): DataSource<List<DataModel>> =
        RoomDataBaseImplementation()
}