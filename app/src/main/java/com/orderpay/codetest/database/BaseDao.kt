package com.orderpay.codetest.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface BaseDao<in E> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAll(entity: List<E>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert(entity: E)
}