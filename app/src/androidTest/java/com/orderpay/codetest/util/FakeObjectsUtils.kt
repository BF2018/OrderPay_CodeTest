package com.orderpay.codetest.util

import com.orderpay.codetest.database.CharacterEntity

object FakeObjectsUtils{
val DBDatabaseCharacter = CharacterEntity(char_id = 1,  name = "Skyler White" , nickname = "Sky" , img = "https://www.globalcosmeticsnews.com/wp-content/uploads/2020/04/Google.png", status = "visible", occupation = listOf()
, appearance = listOf())

val listDBDatabaseCharacter = listOf(DBDatabaseCharacter)
}