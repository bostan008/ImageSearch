package com.deleven.imagesearchtask.base.di

import android.app.Application
import com.deleven.imagesearchtask.base.utils.BaseSchedulerProvider
import com.google.gson.Gson
import dagger.BindsInstance
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ApiModule::class])
interface AppComponent {

    fun schedulerProvider(): BaseSchedulerProvider
    fun retrofitProvider(): Retrofit
    fun gsonProvider(): Gson

    @Component.Builder
    interface Builder{

        fun build(): AppComponent

        @BindsInstance
        fun application(application: Application): Builder
    }
}