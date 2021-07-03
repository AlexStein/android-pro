package ru.softmine.translator.di.module

import dagger.Module
import dagger.Provides
import ru.softmine.translator.model.data.DataModel
import ru.softmine.translator.model.repository.Repository
import ru.softmine.translator.view.MainInteractor
import javax.inject.Named

@Module
class InteractorModule {

    @Provides
    internal fun provideInteractor(
        @Named(NAME_REMOTE) repositoryRemote: Repository<List<DataModel>>,
        @Named(NAME_LOCAL) repositoryLocal: Repository<List<DataModel>>
    ) = MainInteractor(repositoryRemote, repositoryLocal)
}
