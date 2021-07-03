package ru.softmine.translator.di.module

import dagger.Module
import dagger.Provides
import ru.softmine.translator.App

@Module
class AppModule(private val app: App) {
    @Provides
    fun app(): App = app
}