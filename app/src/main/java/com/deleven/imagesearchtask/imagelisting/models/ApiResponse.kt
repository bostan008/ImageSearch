package com.deleven.imagesearchtask.imagelisting.models

import com.google.gson.annotations.SerializedName

data class ApiResponse (

    @SerializedName("total") var total: Int?,
    @SerializedName("totalHits") var totalHits: Int?,
    @SerializedName("hits") var imageLists: MutableList<ImageModel>?
    )