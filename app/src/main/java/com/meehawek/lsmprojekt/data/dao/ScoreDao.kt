package com.meehawek.lsmprojekt.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.google.gson.Gson
import com.meehawek.lsmprojekt.data.Score
import com.meehawek.lsmprojekt.data.FavouriteTeam
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ScoreDao : BaseDao<Score> {

    @Query("SELECT * FROM Score")
    abstract fun getAllScores() : Flow<List<Score>>

    @Query("SELECT * FROM Score WHERE type = :leagueType")
    abstract fun getLeagueScores(leagueType: String) : Flow<List<Score>>

    @Query("SELECT * FROM Score WHERE guests = :name OR hosts = :name")
    abstract fun getScoresByTeam(name: String) : List<Score>

    @Query("DELETE FROM Score")
    abstract fun nukeTable()

}