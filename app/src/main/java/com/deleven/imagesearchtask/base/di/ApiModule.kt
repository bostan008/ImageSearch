package com.deleven.imagesearchtask.base.di

import com.deleven.imagesearchtask.base.utils.BaseConstants.BASE_URL
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class ApiModule {


    @Provides
    @Singleton
    fun providesGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun providesStringConverterFactory() = ScalarsConverterFactory.create()

    @Provides
    @Singleton
    fun providesGsonConverterFactory(gson : Gson) = GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun providesRxJavaCallerAdapterFactory() = RxJava2CallAdapterFactory.create()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
        return builder.build()

    }

    @Provides
    @Singleton
    fun provideRetroFit(rxJava2CallAdapterFactory: RxJava2CallAdapterFactory, gsonConverterFactory: GsonConverterFactory, scalarsConverterFactory: ScalarsConverterFactory, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .addConverterFactory(scalarsConverterFactory)
                .addConverterFactory(gsonConverterFactory)
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build()
    }
}