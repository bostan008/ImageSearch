package com.deleven.imagesearchtask

import com.deleven.imagesearchtask.imagelisting.models.ApiResponse
import com.deleven.imagesearchtask.imagelisting.models.ImageModel


object TestUtil {


    fun dummyApiResponse() : ApiResponse {
        val imageList = mutableListOf<ImageModel>(ImageModel(100,"http://google.com", "http://google.com", "http://google.com", 100, 100))
        return ApiResponse(5000, 1000, imageList)
    }
}