package com.droidcon.rxtoflow.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.droidcon.rxtoflow.domain.Insurance
import java.time.Instant

object InsuranceRespository {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getInsurance(insuranceId: String): Insurance {
        return Insurance(
            insuranceId,
            Instant.now(),
            Instant.now().plusSeconds(43200)
        )
    }
}