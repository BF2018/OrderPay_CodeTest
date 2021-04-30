package com.orderpay.codetest.network

import com.orderpay.codetest.database.CharacterEntity
import com.orderpay.codetest.utils.Constants
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface ApiServices {

    @GET(Constants.API_CHARACTERS_LIST)
    fun getAllCharacters() : Deferred<List<CharacterEntity>>
}