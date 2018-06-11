package com.deleven.imagesearchtask.base.di

import com.deleven.imagesearchtask.base.utils.BaseSchedulerProvider
import com.deleven.imagesearchtask.base.utils.SchedulerProvider
import dagger.Binds
import dagger.Module


@Module
abstract class SchedulerModule {

    @Binds
    abstract fun schedulerProvider(schedulerProvider: SchedulerProvider): BaseSchedulerProvider
}