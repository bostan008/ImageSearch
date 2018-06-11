package com.deleven.imagesearchtask.imagelisting.di

import android.app.Application
import com.deleven.imagesearchtask.base.di.AppComponent
import com.deleven.imagesearchtask.imagelisting.ui.activity.ImageListingActivity
import dagger.BindsInstance
import dagger.Component

@ImageListingScope
@Component(dependencies = [AppComponent::class], modules = [ImageListingModule::class, ImageListingApiModule::class])
interface ImageListingComponent {

    fun inject(imageListingActivity: ImageListingActivity)

    @Component.Builder
    interface Builder{
        fun appComponent(appComponent: AppComponent) : Builder

        fun build() : ImageListingComponent

        @BindsInstance
        fun bind(app: Application) : Builder
    }
}


