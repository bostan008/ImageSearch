package com.deleven.imagesearchtask.base.mvp

interface BaseRepository<in I: BaseRepositoryInteractor> {

    fun setRepositoryInteractor(interactor: I)
    fun clearRepositoryInteractor()
}