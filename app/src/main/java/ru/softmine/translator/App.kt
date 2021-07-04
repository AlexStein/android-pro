package ru.softmine.translator

import android.app.Application
import org.koin.core.context.startKoin
import ru.softmine.translator.di.application
import ru.softmine.translator.di.mainScreen

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(application, mainScreen))
        }
    }
}
