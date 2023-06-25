package com.droidcon.rxtoflow.example

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CompC: ViewModel() {

    fun invoke() {

        runBlocking {
//----------------------------------------Exception in coroutine --------------------------------------------
            val jobRaiseException = GlobalScope.launch { // root coroutine with launch
                println("Throwing exception from launch")
                throw IndexOutOfBoundsException() // Will be printed to the console by Thread.defaultUncaughtExceptionHandler
            }
            jobRaiseException.join()

            /**
                Throwing exception from launch
                Exception in thread "DefaultDispatcher-worker-2 @coroutine#2" java.lang.IndexOutOfBoundsException
             */

//----------------------------------------Handle Exception in coroutine --------------------------------------------
        //----------------------------------------Try catch -----------------------------------

            val launchWithTryCatch = GlobalScope.launch { // root coroutine with launch
                println("Throwing exception from launch")
                try {
                    throw IndexOutOfBoundsException()
                }catch (e: IndexOutOfBoundsException) {
                    println("Caught IndexOutOfBoundsException")
                }

            }
            launchWithTryCatch.join()

            /**
                Throwing exception from launch
                Caught IndexOutOfBoundsException
             */

        //----------------------------------------CoroutineExceptionHandler ----------------------------------

            val handler = CoroutineExceptionHandler { _, exception ->
                println("CoroutineExceptionHandler got $exception")
            }
            val jobCoroutineExceptionHandler = GlobalScope.launch(handler) { // root coroutine, running in GlobalScope
                throw IndexOutOfBoundsException()
            }

            jobCoroutineExceptionHandler.join()

            /**
                CoroutineExceptionHandler got java.lang.IndexOutOfBoundsException
             */


//----------------------------------------Exception in Flow (crash the application))--------------------------------------------
            (1..5).asFlow()
                .map {
                    // raise exception when the value is 3
                    // intentionally for this example
                    check(it != 3) { "Value $it" } // raise exception
                    it * it
                }
                .onCompletion {
                    print("onCompletion")
                }.collect {
                    print("collect : $it")
                }

           /**
            1
            4
            onCompletion
            Exception in thread "main" java.lang.IllegalStateException: Value 3
             */

            //----------------------------------------try catch (Handle exception, no crash)--------------------------------------------
            try {
                (1..5).asFlow()
                    .map {
                        // raise exception when the value is 3
                        // intentionally for this example
                        check(it != 3) { "Value $it" } // raise exception
                        it * it
                    }
                    .onCompletion {
                        print("onCompletion")
                    }.collect {
                        print("collect : $it")
                    }
            } catch (e: Throwable) {
                println("Caught $e")
            }

            /**
             collect : 1
             collect : 4
             onCompletionCaught java.lang.IllegalStateException: Value 3
             */

//----------------------------------------Flow catch builder block (Handle exception, no crash)---------------------------------
            (1..5).asFlow()
                .map {
                    // raise exception when the value is 3,
                    // intentionally for this example
                    check(it != 3) { "Value $it" } // raise exception
                    it * it
                }
                .onCompletion {
                    print("onCompletion")
                }
                .catch { e ->
                    print("collect : $e")
                }
                .collect {
                    print("collect : $it")
                }

            /**
            1
            4
            onCompletion
            Caught java.lang.IllegalStateException: Value 3
             */

        }
    }
}
