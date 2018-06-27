package com.deleven.imagesearchtask.imagelisting.di

import com.deleven.imagesearchtask.imagelisting.domain.api.ImageListingApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ImageListingApiModule {

    @Provides
    @ImageListingScope
    fun provideLocationApi( retrofit: Retrofit): ImageListingApi {
        return retrofit.create(ImageListingApi::class.java)
    }

}