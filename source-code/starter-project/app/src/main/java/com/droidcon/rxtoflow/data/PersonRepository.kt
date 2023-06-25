package com.droidcon.rxtoflow.data

import com.droidcon.rxtoflow.domain.Person
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

object PersonRepository {
    fun getPerson(id: String): Single<Person> {
        return Single.just( Person(
            "DB2672C4-39AB-53E3-198C-85E17B777341",
            "Kato",
            "Barnes",
            "ins_1"
        )
        )
    }

    fun getPersons() : Observable<Person> {
        return Observable.just(
            Person(
            "DB2672C4-39AB-53E3-198C-85E17B777341",
            "Kato",
            "Barnes",
            "ins_1"
        ),
            Person(
                "A6AA9BD0-4856-1BC2-8D86-3752D80894D3",
                "Remedios",
                "Baxter",
                "ins_2"
            ),
            Person(
                "E9EEA706-E0E1-BF54-DE6B-1E682DA7B979",
                "Elizabeth",
                "Baxter",
                "ins_3"
            ),
            Person(
                "F7CA3557-D38B-A759-9917-ED827B5ABEC3",
                "Iris",
                "Clements",
                "ins_4"
            ),
            Person(
                "DCA88526-2459-718F-A050-283596CA57C4",
                "Dolan",
                "Bridges",
                "ins_5"
            )
        )
    }

}