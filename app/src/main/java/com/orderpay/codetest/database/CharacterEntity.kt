package com.orderpay.codetest.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterEntity(
    @PrimaryKey
    var char_id: Int,
    var name: String,
    var nickname: String,
    var img: String,
    var status: String,
    var occupation: List<String>,
    var appearance: List<String>
)
