package com.deleven.imagesearchtask.imagelisting.mvp

import com.deleven.imagesearchtask.base.mvp.BasePresenterImpl
import com.deleven.imagesearchtask.base.utils.BaseSchedulerProvider
import com.deleven.imagesearchtask.base.utils.RxSearchObservable
import com.deleven.imagesearchtask.imagelisting.di.ImageListingScope
import com.deleven.imagesearchtask.imagelisting.domain.repository.ImageListingInteractor
import com.deleven.imagesearchtask.imagelisting.domain.repository.ImageListingRepository
import com.deleven.imagesearchtask.imagelisting.models.ApiResponse
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@ImageListingScope
class ImageListingPresenter @Inject constructor(private val schedulerProvider: BaseSchedulerProvider, private val imageListingRepository: ImageListingRepository)
    : BasePresenterImpl<ImageListingView.View>(), ImageListingView.Presenter, ImageListingInteractor.Interactor {

    init {
        imageListingRepository.setRepositoryInteractor(this)
    }


    override fun showImageListingFromRemote(apiResponse: ApiResponse) {
        compositeDisposable.add(Observable.just(apiResponse).map { it }.observeOn(schedulerProvider.ui()).subscribe(
                {
                    view?.hideLoading()
                    view?.showImageListing(it)
                },
                {
                    view?.hideLoading()
                    it.message?.let { it1 -> view?.onError(it1) }
                }
        ))
    }

    override fun onError(errorMessage: String) {
        view?.hideLoading()
        view?.onError(errorMessage)
    }

    override fun onViewRemoved() {
        super.onViewRemoved()
        imageListingRepository.clearRepositoryInteractor()
    }

    override fun processSearchData(searchObservable: Observable<String>) {
        searchObservable.debounce(400, TimeUnit.MILLISECONDS).subscribeOn(schedulerProvider.computation())
                .observeOn(schedulerProvider.ui()).subscribe({
                    if (it.isNullOrEmpty()) {
                        view?.showNoResultsView()
                    } else {
                        view?.showLoading()
                        imageListingRepository.getImageListingFromRemote(it)
                    }
                }, { onError ->
                    view?.hideLoading()
                    view?.onError(onError.message!!)
                }, {
                    RxSearchObservable.completePublisherForObservable()
                })
    }
}