package com.deleven.imagesearchtask.imagelisting.mvp

import com.deleven.imagesearchtask.base.mvp.BasePresenter
import com.deleven.imagesearchtask.base.mvp.BaseView
import com.deleven.imagesearchtask.imagelisting.models.ApiResponse
import io.reactivex.Observable

interface ImageListingContract {

    interface View : BaseView {
        fun showImageListing(apiResponse: ApiResponse)
        fun onError(errMessage: String)
        fun showNoResultsView()
    }

    interface Presenter : BasePresenter<View> {
        fun processSearchData(searchObservable: Observable<String>)
    }
}