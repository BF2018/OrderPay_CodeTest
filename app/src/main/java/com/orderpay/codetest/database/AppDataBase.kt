package com.orderpay.codetest.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.orderpay.codetest.database.AppDataBase.Companion.dataBaseVersion
import com.orderpay.codetest.utils.Converters

@Database(
    entities = [CharacterEntity::class],
    version = dataBaseVersion,
    exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase(){
    abstract val characterDao : CharacterDao

    companion object {
        const val dataBaseVersion = 1
    }
}