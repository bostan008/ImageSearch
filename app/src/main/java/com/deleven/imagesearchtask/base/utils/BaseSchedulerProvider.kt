package com.deleven.imagesearchtask.base.utils

import io.reactivex.Scheduler

interface BaseSchedulerProvider {

    fun ui(): Scheduler
    fun computation(): Scheduler
    fun io(): Scheduler

}