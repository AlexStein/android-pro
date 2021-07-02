package ru.softmine.translator.di

import dagger.Component
import dagger.android.AndroidInjectionModule
import ru.softmine.translator.App
import ru.softmine.translator.di.module.*
import javax.inject.Singleton


@Component(
    modules = [
        ActivityModule::class,
        AppModule::class,
        InteractorModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
        AndroidInjectionModule::class
    ]
)
@Singleton
interface AppComponent {

    fun inject(app: App)
}
