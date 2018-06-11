package com.deleven.imagesearchtask.base.mvp

import io.reactivex.disposables.CompositeDisposable

open class BaseRepositoryImpl<I: BaseRepositoryInteractor> : BaseRepository<I> {

    val repositoryDisposable = CompositeDisposable()

    var interactor: I? = null
        private set


    override fun setRepositoryInteractor(interactor: I) {
        this.interactor = interactor
    }

    override fun clearRepositoryInteractor() {
        repositoryDisposable.clear()
        interactor = null
    }
}