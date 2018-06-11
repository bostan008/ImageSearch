package com.deleven.imagesearchtask.imagelisting.domain.api

import com.deleven.imagesearchtask.imagelisting.di.ImageListingScope
import com.deleven.imagesearchtask.imagelisting.models.ApiResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

@ImageListingScope
interface ImageListingApi {

    @GET("api/")
    fun getImageListing(@Query("key") apiKey: String,
                        @Query("q") queryString: String, @Query("image_type") imageType: String) : Single<ApiResponse>
}