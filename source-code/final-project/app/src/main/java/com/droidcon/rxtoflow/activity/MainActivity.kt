package com.droidcon.rxtoflow.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.droidcon.rxtoflow.example.CompA
import com.droidcon.rxtoflow.example.CompB
import com.droidcon.rxtoflow.example.CompC
import com.droidcon.rxtoflow.example.CompD
import com.droidcon.rxtoflow.R

class MainActivity : AppCompatActivity() {

    private val compA: CompA by viewModels()
    private val compB: CompB by viewModels()
    private val compC: CompC by viewModels()
    private val compD: CompD by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        compA.invoke()
        compB.invoke()
        compC.invoke()
        compD.invoke()
    }
}