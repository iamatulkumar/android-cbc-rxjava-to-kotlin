package com.droidcon.rxtoflow.example

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.FlowableEmitter
import io.reactivex.rxjava3.schedulers.Schedulers


@SuppressLint("CheckResult")
class CompD: ViewModel() {
    fun invoke() {

        Flowable.create(
            { emitter: FlowableEmitter<Any> ->

                // Publish 1000 numbers
                for (i in 0..999) {
                    println(Thread.currentThread().name + " | Publishing = " + i)
                    emitter.onNext(i)
                }
                // When all values or emitted, call complete.
                emitter.onComplete()
            }, BackpressureStrategy.DROP
        )
            .onBackpressureDrop { i: Any ->
                println(
                    Thread.currentThread().name + " | DROPPED = " + i
                )
            }.subscribeOn(Schedulers.newThread()).observeOn(Schedulers.single())
            .subscribe { i: Any ->
                // Process received value.
                println(Thread.currentThread().name + " | Received = " + i)
                // 500 mills delay to simulate slow subscriber
                Thread.sleep(500)
            }

        /**
        RxNewThreadScheduler-1 | Publishing = 0
        RxSingleScheduler-1 | Received = 0
        RxNewThreadScheduler-1 | Publishing = 1
        RxNewThreadScheduler-1 | Publishing = 2
        RxNewThreadScheduler-1 | Publishing = 3
        RxNewThreadScheduler-1 | Publishing = 4
        RxNewThreadScheduler-1 | Publishing = 5
        .
        .
        RxNewThreadScheduler-1 | Publishing = 126
        RxNewThreadScheduler-1 | Publishing = 127
        RxNewThreadScheduler-1 | Publishing = 128

        RxNewThreadScheduler-1 | DROPPED = 128
        RxNewThreadScheduler-1 | Publishing = 129
        RxNewThreadScheduler-1 | DROPPED = 129
        RxNewThreadScheduler-1 | Publishing = 130
        RxNewThreadScheduler-1 | DROPPED = 130
        RxNewThreadScheduler-1 | Publishing = 131
        RxNewThreadScheduler-1 | DROPPED = 131
        .
        .
        RxNewThreadScheduler-1 | Publishing = 997
        RxNewThreadScheduler-1 | DROPPED = 997
        RxNewThreadScheduler-1 | Publishing = 998
        RxNewThreadScheduler-1 | DROPPED = 998
        RxNewThreadScheduler-1 | Publishing = 999
        RxNewThreadScheduler-1 | DROPPED = 999
        RxSingleScheduler-1 | Received = 1
        RxSingleScheduler-1 | Received = 2
        RxSingleScheduler-1 | Received = 3
        RxSingleScheduler-1 | Received = 4
        RxSingleScheduler-1 | Received = 5
        .
        .
        RxSingleScheduler-1 | Received = 103
        RxSingleScheduler-1 | Received = 106
        .
        .
        RxSingleScheduler-1 | Received = 121
        .
        .
        RxSingleScheduler-1 | Received = 126
        RxSingleScheduler-1 | Received = 127
         */

    }
}