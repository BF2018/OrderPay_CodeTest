package com.orderpay.codetest.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface CharacterDao : BaseDao<CharacterEntity>{

    @Query("select * from CharacterEntity")
    fun getAllCharactersFromDB() : LiveData<List<CharacterEntity>>


    //Dao query with season - For filter functionality
    @Query("select * from CharacterEntity where appearance LIKE :season")
    fun getLocalCharactersByAppearance(season: String) : LiveData<List<CharacterEntity>>
}