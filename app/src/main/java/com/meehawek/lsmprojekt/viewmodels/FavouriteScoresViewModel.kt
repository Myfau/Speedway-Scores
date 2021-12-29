package com.meehawek.lsmprojekt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.meehawek.lsmprojekt.R
import com.meehawek.lsmprojekt.data.dao.FavouriteTeamDao
import com.meehawek.lsmprojekt.data.Repository
import com.meehawek.lsmprojekt.data.Score
import com.meehawek.lsmprojekt.data.FavouriteTeam
import com.meehawek.lsmprojekt.ui.base.NavigEvent
import com.meehawek.lsmprojekt.ui.base.NavigateEventInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class FavouriteScoresViewModel @Inject public constructor(
    private val favouritesRepository: Repository,
    val favouriteTeamDao: FavouriteTeamDao
) : BaseViewModel() {

    fun getFavTeamScores(): MutableLiveData<List<Score>> {
            val favTeamScores : MutableList<Score> = mutableListOf()
            favouritesRepository.getAllFavourites().forEach {
                val temp = favouritesRepository.getScoresByTeam(it.name)
                temp.forEach { s ->
                    favTeamScores.add(s)
                }
            }
            var ld : MutableLiveData<List<Score>> = MutableLiveData()
            ld.postValue(favTeamScores)
        return ld
    }

    var favScores = getFavTeamScores()

    fun remFavHost(obj: String){
        GlobalScope.async {
            val remFav = FavouriteTeam(name = obj)
            favouriteTeamDao.delete(remFav)
            favScores = getFavTeamScores()
        }
    }

    fun remFavGuest(obj: String){
        GlobalScope.async {
            val remFav = FavouriteTeam(name = obj)
            favouriteTeamDao.delete(remFav)
            favScores = getFavTeamScores()
        }
    }

    fun updateData(){
        GlobalScope.async {
            favScores = getFavTeamScores()
        }
    }

}