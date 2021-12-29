package com.meehawek.lsmprojekt.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.meehawek.lsmprojekt.data.Score
import com.meehawek.lsmprojekt.data.FavouriteTeam
import kotlinx.coroutines.flow.Flow

@Dao
abstract class FavouriteTeamDao : BaseDao<FavouriteTeam> {

    @Query("SELECT name FROM FavouriteTeam")
    abstract fun getFavouriteTeams() : List<FavouriteTeam>

    @Query("DELETE FROM FavouriteTeam WHERE name LIKE :n")
    abstract fun removeFromFav(n : String)
}