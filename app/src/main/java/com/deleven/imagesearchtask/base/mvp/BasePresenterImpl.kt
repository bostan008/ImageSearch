package com.deleven.imagesearchtask.base.mvp

import io.reactivex.disposables.CompositeDisposable

open class BasePresenterImpl<T: BaseView> : BasePresenter<T> {

    val compositeDisposable = CompositeDisposable()

    var view: T? = null
        private set

    override fun onViewAdded(view: T) {
        this.view = view
    }

    override fun onViewRemoved() {
        compositeDisposable.clear()
        view = null
    }
}