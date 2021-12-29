package com.meehawek.lsmprojekt.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Score (
        @PrimaryKey(autoGenerate = true) val vid: Long = 0,
        var score: String,
        var queue: Int,
        var date: String,
        var guests: String,
        var hosts: String,
        var type: String
)