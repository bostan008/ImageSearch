package com.deleven.imagesearchtask.imagelisting.domain.repository

import com.deleven.imagesearchtask.base.mvp.BaseRepository
import com.deleven.imagesearchtask.base.mvp.BaseRepositoryInteractor
import com.deleven.imagesearchtask.imagelisting.models.ApiResponse

interface ImageListingInteractor {

    interface Interactor : BaseRepositoryInteractor {
        fun showImageListingFromRemote(apiResponse: ApiResponse)
        fun onError(errorMessage: String)
    }

    interface Call : BaseRepository<Interactor> {
        fun getImageListingFromRemote(queryString: String)
    }
}