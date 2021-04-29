package com.orderpay.codetest.di

import com.orderpay.codetest.network.ApiServices
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    fun provideUserApi(retrofit: Retrofit): ApiServices {
        return retrofit.create(ApiServices::class.java)
    }

    single { provideUserApi(get()) }
}