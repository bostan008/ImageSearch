package com.deleven.imagesearchtask.base.mvp

interface BasePresenter<in T : BaseView> {

    fun onViewAdded(view: T)
    fun onViewRemoved()
}