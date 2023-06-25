package com.droidcon.rxtoflow.example

import android.util.Log
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableSource
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class CompC: ViewModel() {

    fun invoke(){
//----------------------------------------onErrorResumeWith--------------------------------------------
        Observable.just(1, 2, 3, 4)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                if (it == 2) {
                    throw (RuntimeException("Exception on 2"))
                }
            }.onErrorResumeWith {
                Log.d("onErrorResumeWith : ","1")
            }
            .subscribe(object : Observer<Int> {
                override fun onComplete() {
                    Log.d("onComplete ", "Completed")
                }
                override fun onSubscribe(d: Disposable) {
                }
                override fun onNext(i: Int) {
                    Log.d("onNext : ", i.toString())
                }
                override fun onError(e: Throwable) {
                    Log.d("onError", e.localizedMessage)
                }
            })

        /**
            onNext : 1
            onErrorResumeWith : 1
         */

//----------------------------------------onErrorResumeNext--------------------------------------------
        Observable.just(1, 2, 3, 4)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { x ->
                if (x == 2) {
                    throw NullPointerException("Its a NPE")
                }
                x*10
            }.onErrorResumeNext { throwable: Throwable ->
                return@onErrorResumeNext ObservableSource {
                    Log.d("onErrorResumeNext", throwable.localizedMessage)
                }
            }.subscribe(object : Observer<Int> {
                override fun onComplete() {
                    Log.d("onComplete", "Completed")
                }
                override fun onSubscribe(d: Disposable) {
                }
                override fun onNext(i: Int) {
                    Log.d("onNext", i.toString())
                }
                override fun onError(e: Throwable) {
                    Log.d("onError", e.localizedMessage)
                }
            })
        /**
            onNext: 1
            onErrorResumeNext: Its a NPE
         */

//----------------------------------------onErrorComplete--------------------------------------------
        Observable.just(1, 2, 3, 4)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                if (it == 2) {
                    throw (RuntimeException("Exception on 2"))
                }
            }
            .onErrorComplete {
                Log.d("onErrorComplete", "Exception Occurred")
                true
            }.subscribe(object : Observer<Int> {
                override fun onComplete() {
                    Log.d("onComplete", "Completed")
                }
                override fun onSubscribe(d: Disposable) {
                }
                override fun onNext(i: Int) {
                    Log.d("onNext", i.toString())
                }
                override fun onError(e: Throwable) {
                    Log.d("onError", e.localizedMessage)
                }
            })
        /**
        onNext: 1
        onErrorComplete: Exception Occurred
        onComplete: Completed
         */

//----------------------------------------onErrorReturn--------------------------------------------
        Observable.just(1, 2, 3, 4)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                if (it == 2) {
                    throw (RuntimeException("Exception on 2"))
                }
            }
            .onErrorReturn { t: Throwable ->
                Log.d("onComplete", t.localizedMessage)
                5
            }.subscribe(object : Observer<Int> {
                override fun onComplete() {
                    Log.d("onComplete", "Completed")
                }
                override fun onSubscribe(d: Disposable) {
                }
                override fun onNext(i: Int) {
                    Log.d("onNext", i.toString())
                }
                override fun onError(e: Throwable) {
                    Log.d("onError", e.localizedMessage)
                }
            })

        /**
         *  onComplete: Exception on 2
            onNext: 5
            onComplete: Completed
         */

//----------------------------------------onErrorReturnItem--------------------------------------------
        Observable.just(1, 2, 3, 4)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                if (it == 2) {
                    throw (RuntimeException("Exception on 2"))
                }
            }
            .onErrorReturnItem(10)
            .subscribe(object : Observer<Int> {
                override fun onComplete() {
                    Log.d("onComplete ", "Completed")
                }
                override fun onSubscribe(d: Disposable) {
                }
                override fun onNext(i: Int) {
                    Log.d("onNext", i.toString())
                }
                override fun onError(e: Throwable) {
                    Log.d("onError", e.localizedMessage)
                }
            })
        /**
        onNext: 1
        onNext: 10
        onComplete: Completed
         */

    }
}

