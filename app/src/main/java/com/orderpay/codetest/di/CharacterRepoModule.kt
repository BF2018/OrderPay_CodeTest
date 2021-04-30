package com.orderpay.codetest.di

import com.orderpay.codetest.database.AppDataBase
import com.orderpay.codetest.network.ApiServices
import com.orderpay.codetest.repository.CharactersRepository
import org.koin.dsl.module

val characterRepoModule = module {
    fun provideCharacterRepository(api: ApiServices, dao: AppDataBase): CharactersRepository {
        return CharactersRepository(api, dao)
    }
    single { provideCharacterRepository(get(), get()) }
}