package com.droidcon.rxtoflow.example

import androidx.lifecycle.ViewModel
import com.droidcon.rxtoflow.domain.Car
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking

class CompA: ViewModel() {
    private val car = Car("Skoda")

    fun invoke() {

        runBlocking {

//------------- Flow event ----------------------
            //fun flowEvent(): Flow<String> {
            //    return flowOf("1", "2", "3", "4", "5")
            //}
            flowEvent()
                .flowOn(Dispatchers.Default)
                .collect(::println)

            /**
                1
                2
                3
                4
                5
             */

//------------- Fetch single value ----------------------

//            suspend fun fetchSingleValue(car: Car): Car {
//                return car
//            }
            fetchSingleValue(car)

            /**
              Car(name=Skoda)
             */


//------------- Fetch single value, when pass null value----------------------
//            suspend fun fetchSingleNullableValue(car: Car?): Car? {
//                return car
//            }

            fetchSingleNullableValue(null)

            /**
              null
             */


//------------- Fetch single value, when pass non Null value----------------------
//            suspend fun fetchSingleNullableValue(car: Car?): Car? {
//                return car
//            }
            fetchSingleNullableValue(car)

            /**
             Car(name=Skoda)
             */


//------------- Complete with no specific result----------------------
//            suspend fun completeWithOutResult(car: Car) {
//                println(car)
//            }
            completeWithOutResult (car)

            /**
                Car(name=Skoda)
             */
        }

    }



    // 0-n events, the backpressure is automatically taken care off
    fun flowEvent(): Flow<String> {
        return flowOf("1", "2", "3", "4", "5")
    }

    // exactly 1 event
    suspend fun fetchSingleValue(car: Car): Car {
        return car
    }

    // 0-1 events
    //Kotlin Flow / Coroutines support "null" values
    suspend fun fetchSingleNullableValue(car: Car?): Car? {
        return car
    }

    // just completes with no specific results
    suspend fun completeWithOutResult(car: Car) {
        println(car)
    }







    // Code challenge
    suspend fun fetchCarValue(car: Car?): Car {
        return car!!
    }
    // fetchCarValue(null)


}



// Code challenge solution
fun main() {
    suspend fun fetchCarValue(car: Car?): Car {
        return car!!
    }

    runBlocking {
        fetchCarValue(null)
    }

    /**
     * throw null pointer exception
     */

}













