package com.droidcon.rxtoflow.domain

import java.time.Instant

data class Insurance(
    val insuranceId:String,
    val startDate: Instant,
    val endDate: Instant,
)