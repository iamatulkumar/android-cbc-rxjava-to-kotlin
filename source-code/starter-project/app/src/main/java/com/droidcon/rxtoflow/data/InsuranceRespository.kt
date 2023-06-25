package com.droidcon.rxtoflow.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.droidcon.rxtoflow.domain.Insurance
import io.reactivex.rxjava3.core.Single
import java.time.Instant

object InsuranceRespository {
    @RequiresApi(Build.VERSION_CODES.O)
    fun getInsurance(insuranceId: String): Single<Insurance> {
        return Single.just( Insurance(
            insuranceId,
            Instant.now(),
            Instant.now().plusSeconds(43200)
        )
        )
    }
}