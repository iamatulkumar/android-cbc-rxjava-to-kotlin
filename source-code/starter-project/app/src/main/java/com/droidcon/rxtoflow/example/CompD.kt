package com.droidcon.rxtoflow.example

import android.util.Log
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class CompD: ViewModel() {
    fun invoke() {
        Observable.range(1, 1_000_000)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toFlowable(BackpressureStrategy.DROP)
            .doOnNext {
                Log.d("doOnNext", "$it")
            }
            .subscribe()

        /**
         Drop the element
         1
         2...
         200...
         4689...
         */
    }
}