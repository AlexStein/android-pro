package ru.softmine.translator.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.softmine.translator.ui.MainActivity

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}
