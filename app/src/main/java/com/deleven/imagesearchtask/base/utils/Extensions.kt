package com.deleven.imagesearchtask.base.utils

import android.app.Activity
import android.content.res.Resources
import android.view.View


fun Activity.getWidth() = Resources.getSystem().displayMetrics.widthPixels

fun View.getDeviceWidth() = Resources.getSystem().displayMetrics.widthPixels