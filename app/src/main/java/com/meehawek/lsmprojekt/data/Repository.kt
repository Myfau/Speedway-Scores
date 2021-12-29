package com.meehawek.lsmprojekt.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.meehawek.lsmprojekt.data.dao.FavouriteTeamDao
import com.meehawek.lsmprojekt.data.FavouriteTeam
import com.meehawek.lsmprojekt.data.dao.ScoreDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    val scoreDao: ScoreDao,
    val favouriteTeamDao: FavouriteTeamDao
) {
    fun getAllScores() = scoreDao.getAllScores()
    fun getAllFavourites() = favouriteTeamDao.getFavouriteTeams()
    fun getLeagueScores(leagueType: String) = scoreDao.getLeagueScores(leagueType)
    fun getScoresByTeam(name: String) = scoreDao.getScoresByTeam(name)
}
