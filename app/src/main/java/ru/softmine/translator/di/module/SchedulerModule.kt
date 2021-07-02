package ru.softmine.translator.di.module

import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Named

@Module
class SchedulerModule {

    @Named("ui")
    @Provides
    fun uiScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Named("io")
    @Provides
    fun ioScheduler(): Scheduler = Schedulers.io()
}