package com.deleven.imagesearchtask.imagelisting.domain.repository

import com.deleven.imagesearchtask.imagelisting.models.ApiResponse

interface ImageListingRepository {

    interface ImageListingCallback {
        fun showImageListingFromRemote(apiResponse: ApiResponse)
        fun onError(errorMessage: String)
    }

    fun getImageListingFromRemote(queryString: String, imageListingCallback: ImageListingCallback)

    fun clearRepository()


}