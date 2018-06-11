package com.deleven.imagesearchtask.imagelisting.di

import com.deleven.imagesearchtask.imagelisting.mvp.ImageListingPresenter
import com.deleven.imagesearchtask.imagelisting.mvp.ImageListingView
import dagger.Module
import dagger.Provides


@Module
class ImageListingModule {

    @Provides
    @ImageListingScope
    fun provideImageListingPresenter(presenter: ImageListingPresenter): ImageListingView.Presenter {
        return presenter
    }
}