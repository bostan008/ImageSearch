package com.deleven.imagesearchtask.imagelisting.di

import com.deleven.imagesearchtask.base.utils.BaseSchedulerProvider
import com.deleven.imagesearchtask.imagelisting.domain.api.ImageListingApi
import com.deleven.imagesearchtask.imagelisting.domain.repository.ImageListingRepository
import com.deleven.imagesearchtask.imagelisting.domain.repository.ImageListingRepositoryImpl
import com.deleven.imagesearchtask.imagelisting.mvp.ImageListingContract
import com.deleven.imagesearchtask.imagelisting.mvp.ImageListingPresenter
import dagger.Module
import dagger.Provides


@Module
class ImageListingModule {

    @Provides
    @ImageListingScope
    fun provideImageListingPresenter(presenter: ImageListingPresenter): ImageListingContract.Presenter {
        return presenter
    }

    @Provides
    @ImageListingScope
    fun provideImageListingRepository(imageListingApi: ImageListingApi, schedulerProvider: BaseSchedulerProvider): ImageListingRepository {
        return ImageListingRepositoryImpl(imageListingApi, schedulerProvider)
    }
}