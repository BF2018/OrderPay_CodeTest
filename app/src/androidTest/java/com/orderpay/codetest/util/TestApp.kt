package com.orderpay.codetest.util

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TestApp : Application() {
    override fun onCreate() {
        super.onCreate()

        /**
         * Start Koin
         */
        startKoin {
          androidContext(  this@TestApp)
            modules(listOf())
        }}
}