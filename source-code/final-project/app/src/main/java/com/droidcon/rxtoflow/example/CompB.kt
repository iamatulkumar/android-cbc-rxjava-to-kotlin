package com.droidcon.rxtoflow.example

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.droidcon.rxtoflow.domain.Insurance
import com.droidcon.rxtoflow.data.InsuranceRespository
import com.droidcon.rxtoflow.data.PersonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class CompB : ViewModel(){

    @RequiresApi(Build.VERSION_CODES.O)
    fun invoke(){
        runBlocking {
//------------- Calling fetch insurance suspending function----------------------
            val insuranceId = "ins_1"
            fetchInsurance(insuranceId)

            /**
            Insurance(insuranceId=ins_1, startDate=2023-06-18T17:26:00.792641Z, endDate=2023-06-18T17:26:00.792651Z)
             */

//------------- Calling get person suspending function and fetch their insurance detail-------------
            val id = "DB2672C4-39AB-53E3-198C-85E17B777341"
            getPersonInsurance(id)

            /**
            Insurance(insuranceId=ins_1, startDate=2023-06-18T17:29:20.186865Z, endDate=2023-06-18T17:29:20.186870Z)
             */

//------------- Calling get persons suspending function, and fetch corresponding insurance details----------
            observePersonsInsurances()
                .flowOn(Dispatchers.IO)
                .collect(::println)
            /**
            Insurance(insuranceId=ins_1, startDate=2023-06-19T05:34:50.848291Z, endDate=2023-06-19T05:34:50.848296Z)
            Insurance(insuranceId=ins_2, startDate=2023-06-19T05:34:50.876474Z, endDate=2023-06-19T05:34:50.876476Z)
            Insurance(insuranceId=ins_3, startDate=2023-06-19T05:34:50.876615Z, endDate=2023-06-19T05:34:50.876617Z)
            Insurance(insuranceId=ins_4, startDate=2023-06-19T05:34:50.876682Z, endDate=2023-06-19T05:34:50.876684Z)
            Insurance(insuranceId=ins_5, startDate=2023-06-19T05:34:50.876743Z, endDate=2023-06-19T05:34:50.876744Z)
             */

        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun fetchInsurance(insuranceId: String): Insurance {
        return InsuranceRespository.getInsurance(insuranceId) // calling fetchInsurance suspend function
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getPersonInsurance(id: String): Insurance {
        val person = PersonRepository.getPerson(id) // fetch single person suspend function
        return fetchInsurance(person.insuranceId) // fetch single person insurance with insuranceId
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun observePersonsInsurances(): Flow<Insurance> {
        return PersonRepository.getPersons() // fetch persons list
            .map { person ->
                fetchInsurance(person.insuranceId) // calling fetchInsurance suspend function
            }
    }
}
