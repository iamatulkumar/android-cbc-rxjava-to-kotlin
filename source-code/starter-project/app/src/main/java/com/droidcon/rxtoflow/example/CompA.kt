package com.droidcon.rxtoflow.example

import android.util.Log
import androidx.lifecycle.ViewModel
import com.droidcon.rxtoflow.domain.Car
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class CompA: ViewModel() {

    private val car = Car("HondaCity")

    fun invoke() {
        Log.d("onStart :", "observeEvents")
        observeEvents()
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                Log.d("onNext : ", it)
            }
            .subscribe()

            /**
            onNext : 1
            onNext : 2
            onNext : 3
            onNext : 4
            onNext : 5
            onComplete Completed
             */

        Log.d("onStart :", "flowableEvents")
        flowableEvents()
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                Log.d("onNext : ", it)
            }
            .subscribe()

        /**
        onNext : 1
        onNext : 2
        onNext : 3
        onNext : 4
        onNext : 5
        onComplete Completed
         */

        Log.d("onStart :", "singleEvent")
        singleEvent(car)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .doOnSuccess(::println)
            .subscribe()
        /**
         Car(name=HondaCity)
         */

        Log.d("onStart :", "maybeEvent")
        maybeEvent(car)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                Log.d("onSuccess : ", "$it")
            }
            .subscribe()

        /**
        onSuccess : Car(name=HondaCity)
         */

        Log.d("onStart :", "completableEvent")
        completableEvent(car)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                Log.d("onComplete : ", "Completed")
            }
            .subscribe()
        /**
        onComplete : Completed
         */
    }

    // 0-n events without backpressure management
    private fun observeEvents(): Observable<String> {
        return Observable.just("1", "2", "3", "4", "5")
    }

    // 0-n events with explicit backpressure management, used for large data
    private fun flowableEvents(): Flowable<String> {
        return Flowable.just("1", "2", "3", "4", "5")
    }

    // exactly 1 event
    private fun singleEvent(car: Car): Single<Car> {
        return Single.just(car)
    }

    // 0-1 events // RxKotlin that emits an error, single value, or no value use case for db operation update delete
    private fun maybeEvent(car: Car?): Maybe<Car> {
        return car?.let {
            Maybe.just(car)
        }?:Maybe
            .empty<Car>()
    }

    // just completes with no specific results, Example use case(Save Data in cache)
    private fun completableEvent(car: Car): Completable {
        return Completable
            .fromAction {
                println("$car")
            }
    }
}

