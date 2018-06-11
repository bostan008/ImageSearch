package com.deleven.imagesearchtask.base.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

object RxSearchObservable {

    private var subject: PublishSubject<String>? = null

    fun fromView(searchView: EditText): Observable<String> {
        subject = PublishSubject.create<String>()

        searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                println("+++++++ ontext changed ++++++++")
                subject?.onNext(charSequence.toString())
            }

            override fun afterTextChanged(editable: Editable) {
                println("+++++++ afterTextChanged ++++++++")

            }
        })

        return subject!!
    }

    fun completePublisherForObservable(){
        subject?.onComplete()
    }
}