package com.deleven.imagesearchtask.imagelisting.mvp

import com.deleven.imagesearchtask.base.mvp.BasePresenterImpl
import com.deleven.imagesearchtask.base.utils.BaseSchedulerProvider
import com.deleven.imagesearchtask.base.utils.RxSearchObservable
import com.deleven.imagesearchtask.imagelisting.di.ImageListingScope
import com.deleven.imagesearchtask.imagelisting.domain.repository.ImageListingRepository
import com.deleven.imagesearchtask.imagelisting.models.ApiResponse
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@ImageListingScope
class ImageListingPresenter @Inject constructor(private val schedulerProvider: BaseSchedulerProvider, private val imageListingRepository: ImageListingRepository)
    : BasePresenterImpl<ImageListingContract.View>(), ImageListingContract.Presenter {

    override fun onViewRemoved() {
        super.onViewRemoved()
        imageListingRepository.clearRepository()
        RxSearchObservable.completePublisherForObservable()
    }

    override fun processSearchData(searchObservable: Observable<String>) {
        searchObservable.debounce(400, TimeUnit.MILLISECONDS).subscribeOn(schedulerProvider.computation())
                .observeOn(schedulerProvider.ui()).subscribe({
                    if (it.isNullOrEmpty()) {
                        view?.showNoResultsView()
                    } else {
                        view?.showLoading()
                        getImageListing(it)
                    }
                }, { onError ->
                    view?.hideLoading()
                    view?.onError(onError.message!!)
                }, {
                   // RxSearchObservable.completePublisherForObservable()
                })
    }

    fun getImageListing(search: String){
        imageListingRepository.getImageListingFromRemote(search, object : ImageListingRepository.ImageListingCallback{
            override fun showImageListingFromRemote(apiResponse: ApiResponse) {
                view?.hideLoading()
                view?.showImageListing(apiResponse)
            }

            override fun onError(errorMessage: String) {
                view?.hideLoading()
                view?.onError(errorMessage)
            }
        })
    }


}