package com.meehawek.lsmprojekt.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavouriteTeam(
    @PrimaryKey val name: String,
){
}
