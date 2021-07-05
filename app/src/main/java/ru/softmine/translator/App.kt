package ru.softmine.translator

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.softmine.translator.di.application
import ru.softmine.translator.di.descriptionScreen
import ru.softmine.translator.di.historyScreen
import ru.softmine.translator.di.mainScreen

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(listOf(application, mainScreen, historyScreen, descriptionScreen))
        }
    }
}
