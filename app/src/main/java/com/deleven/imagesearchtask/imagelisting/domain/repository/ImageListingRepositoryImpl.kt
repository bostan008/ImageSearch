package com.deleven.imagesearchtask.imagelisting.domain.repository

import com.deleven.imagesearchtask.base.utils.BaseConstants
import com.deleven.imagesearchtask.base.utils.BaseSchedulerProvider
import com.deleven.imagesearchtask.imagelisting.di.ImageListingScope
import com.deleven.imagesearchtask.imagelisting.domain.api.ImageListingApi
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@ImageListingScope
class ImageListingRepositoryImpl @Inject constructor(private val imageListingApi: ImageListingApi, private val schedulerProvider: BaseSchedulerProvider)
    : ImageListingRepository {

    private val repositoryDisposable = CompositeDisposable()
    private var imageListingCallback: ImageListingRepository.ImageListingCallback? = null

    override fun clearRepository() {
        repositoryDisposable.clear()
        imageListingCallback = null
    }


    override fun getImageListingFromRemote(queryString: String, imageListingCallback: ImageListingRepository.ImageListingCallback) {
        this.imageListingCallback = imageListingCallback

        repositoryDisposable.add(
                imageListingApi.getImageListing(BaseConstants.API_KEY, queryString, BaseConstants.IMAGE_TYPE).subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe({
                            this.imageListingCallback?.showImageListingFromRemote(it)
                        }, {
                            this.imageListingCallback?.onError(it.message!!)
                        })

        )

    }


}