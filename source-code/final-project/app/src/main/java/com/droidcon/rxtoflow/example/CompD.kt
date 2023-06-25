package com.droidcon.rxtoflow.example

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

class CompD: ViewModel() {

    fun invoke(){
        runBlocking {

            flow {
                for (i in 1..10) {
                    println("Emiting $i")
                    emit(i)
                }
            }.buffer(1, BufferOverflow.SUSPEND)
                .collect {
                        value ->
                    delay(100)
                    println("Consuming $value")
                }

            /**
            Emiting 1
            Emiting 2
            Emiting 3
            Consuming 1
            Emiting 4
            Consuming 2
            Emiting 5
            Consuming 3
            Emiting 6
            Consuming 4
            Emiting 7
            Consuming 5
            Emiting 8
            Consuming 6
            Emiting 9
            Consuming 7
            Emiting 10
            Consuming 8
            Consuming 9
            Consuming 10
             */
        }
        }
    }

