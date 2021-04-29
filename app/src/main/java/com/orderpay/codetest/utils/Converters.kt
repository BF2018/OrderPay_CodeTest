package com.orderpay.codetest.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {
    companion object {
        var gson = Gson()

        @TypeConverter
        @JvmStatic
        fun stringToStringList(data: String?): List<String> {
            return data?.run {
                val listType: Type =
                    object : TypeToken<List<String?>?>() {}.type
                gson.fromJson<List<String>>(data, listType)
            } ?: listOf()
        }

        @TypeConverter
        @JvmStatic
        fun stringListToString(stringList: List<String?>?): String = gson.toJson(stringList)
    }
}