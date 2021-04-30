package com.orderpay.codetest.repository

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.orderpay.codetest.database.AppDataBase
import com.orderpay.codetest.database.CharacterDao
import org.junit.After
import org.junit.Before
import java.lang.Exception

open class DatabaseTest {
    // system under test
    protected lateinit var database: AppDataBase
    val characterDao: CharacterDao
        get() = database.characterDao

    @Before
    open fun init() {
        //Create a temporally database
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDataBase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun finish() {
        try {
            database.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}