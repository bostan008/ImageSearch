package com.deleven.imagesearchtask

import android.app.Application
import com.deleven.imagesearchtask.base.di.AppComponent
import com.deleven.imagesearchtask.base.di.DaggerAppComponent

class App: Application() {

    lateinit var appComponent: AppComponent
        private set

    companion object {
        lateinit var app: App
            internal set
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        appComponent = DaggerAppComponent.builder().application(app).build()
    }


}