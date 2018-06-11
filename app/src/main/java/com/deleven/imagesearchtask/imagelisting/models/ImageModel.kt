package com.deleven.imagesearchtask.imagelisting.models

import com.google.gson.annotations.SerializedName

data class ImageModel(

        @SerializedName("id") var imageId: Int?,
        @SerializedName("previewURL") var previewUrl: String?,
        @SerializedName("largeImageURL") var largeUrl: String?,
        @SerializedName("webformatURL") var webFormatUrl: String?,
        @SerializedName("likes") var likes: Int?,
        @SerializedName("favorites") var favorites: Int?
)