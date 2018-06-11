package com.deleven.imagesearchtask.imagelisting.domain.repository

import com.deleven.imagesearchtask.base.mvp.BaseRepositoryImpl
import com.deleven.imagesearchtask.base.utils.BaseConstants
import com.deleven.imagesearchtask.base.utils.BaseSchedulerProvider
import com.deleven.imagesearchtask.imagelisting.di.ImageListingScope
import com.deleven.imagesearchtask.imagelisting.domain.api.ImageListingApi
import javax.inject.Inject

@ImageListingScope
class ImageListingRepository @Inject constructor(private val imageListingApi: ImageListingApi, private val schedulerProvider: BaseSchedulerProvider)
    : BaseRepositoryImpl<ImageListingInteractor.Interactor>(), ImageListingInteractor.Call {


    override fun getImageListingFromRemote(queryString: String) {

        repositoryDisposable.add(
                imageListingApi.getImageListing(BaseConstants.API_KEY, queryString, BaseConstants.IMAGE_TYPE).subscribeOn(schedulerProvider.io())
                        .subscribe({
                            interactor?.showImageListingFromRemote(it)
                        }, {
                            interactor?.onError(it.message!!)
                        })

        )


    }

}