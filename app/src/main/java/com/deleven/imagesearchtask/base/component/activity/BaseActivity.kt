package com.deleven.imagesearchtask.base.component.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.deleven.imagesearchtask.App
import com.deleven.imagesearchtask.base.di.AppComponent
import com.deleven.imagesearchtask.base.mvp.BasePresenter
import com.deleven.imagesearchtask.base.mvp.BaseView
import javax.inject.Inject

abstract class BaseActivity<P : BasePresenter<V>, V : BaseView> : AppCompatActivity() {

    @Inject
    lateinit var presenter: P
        internal set


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDagger(App.app.appComponent)
        presenter.onViewAdded(getPresenterView())

    }

    abstract fun getPresenterView(): V

    protected abstract fun initDagger(appComponent: AppComponent)

    override fun onDestroy() {

        if (this::presenter.isInitialized) {
            presenter.onViewRemoved()
        }

        super.onDestroy()
    }

}