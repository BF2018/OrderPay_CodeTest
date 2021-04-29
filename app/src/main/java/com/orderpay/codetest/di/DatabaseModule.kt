package com.orderpay.codetest.di

import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import com.orderpay.codetest.database.AppDataBase
import com.orderpay.codetest.database.CharacterDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    @VisibleForTesting
    val databaseName = "breaking-bad-db"

    fun provideDatabase(application: Application) : AppDataBase {
        return Room.databaseBuilder(application, AppDataBase::class.java,databaseName)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    fun provideDao(dataBase: AppDataBase) : CharacterDao{
        return dataBase.characterDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
}