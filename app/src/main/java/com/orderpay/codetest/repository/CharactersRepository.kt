package com.orderpay.codetest.repository

import com.orderpay.codetest.database.AppDataBase
import com.orderpay.codetest.network.ApiServices
import com.orderpay.codetest.utils.OpenForTesting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

@OpenForTesting
class CharactersRepository(private val apiServices: ApiServices, private val database : AppDataBase) {

    suspend fun refreshCharacters() {
        withContext(Dispatchers.IO) {
            Timber.d("refresh characters is called")
            val characterList = apiServices.getAllCharacters().await()
            database.characterDao.insertAll(characterList)
        }
    }
}