package com.droidcon.rxtoflow.example

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.droidcon.rxtoflow.data.InsuranceRespository
import com.droidcon.rxtoflow.domain.Insurance
import com.droidcon.rxtoflow.data.PersonRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class CompB : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    fun invoke() {
        val insuranceId = "ins_1"
        fetchInsurance(insuranceId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                Log.d("doOnSuccess", "$it")
            }
            .subscribe()
        /**
        Insurance(insuranceId=ins_1, startDate=2023-06-18T17:26:00.792641Z, endDate=2023-06-18T17:26:00.792651Z)
         */

        val id = "DB2672C4-39AB-53E3-198C-85E17B777341"
        getPersonInsurance(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                Log.d("doOnSuccess", "$it")
            }
            .subscribe()
        /**
        Insurance(insuranceId=ins_1, startDate=2023-06-18T17:29:20.186865Z, endDate=2023-06-18T17:29:20.186870Z)
         */

        observePersonsInsurances()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                Log.d("doOnNext", "$it")
            }
            .subscribe()
        /**
        Insurance(insuranceId=ins_1, startDate=2023-06-19T05:34:50.848291Z, endDate=2023-06-19T05:34:50.848296Z)
        Insurance(insuranceId=ins_3, startDate=2023-06-19T05:34:50.876615Z, endDate=2023-06-19T05:34:50.876617Z)
        Insurance(insuranceId=ins_4, startDate=2023-06-19T05:34:50.876682Z, endDate=2023-06-19T05:34:50.876684Z)
        Insurance(insuranceId=ins_2, startDate=2023-06-19T05:34:50.876474Z, endDate=2023-06-19T05:34:50.876476Z)
        Insurance(insuranceId=ins_5, startDate=2023-06-19T05:34:50.876743Z, endDate=2023-06-19T05:34:50.876744Z)
         */
    }

    // return person insurance
    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchInsurance(insuranceId: String): Single<Insurance> {
        return InsuranceRespository.getInsurance(insuranceId) // return single insurance value
    }

    // fetch a single person insurance
    @RequiresApi(Build.VERSION_CODES.O)
    fun getPersonInsurance(id: String): Single<Insurance> {
        return PersonRepository.getPerson(id) // fetch Single Person value
            .flatMap { person ->
                fetchInsurance(person.insuranceId) // return single insurance value
            }
    }

    // fetch a multiple persons insurance
    @RequiresApi(Build.VERSION_CODES.O)
    fun observePersonsInsurances(): Observable<Insurance> {
        return  PersonRepository.getPersons() // fetch all person list
            .flatMap { person ->
                fetchInsurance(person.insuranceId) // fetch insurance value
                    .toObservable() // flatMap expect an Observable
            } // return all person's insurance value
    }

}
