package com.meehawek.lsmprojekt.viewmodels

import androidx.lifecycle.asLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.meehawek.lsmprojekt.R
import com.meehawek.lsmprojekt.data.FavouriteTeam
import com.meehawek.lsmprojekt.data.Repository
import com.meehawek.lsmprojekt.data.Score
import com.meehawek.lsmprojekt.ui.base.NavigEvent
import com.meehawek.lsmprojekt.ui.base.NavigateEventInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.net.URL
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection


@HiltViewModel
class ScoreViewModel @Inject public constructor(
    private val repository: Repository,
) : BaseViewModel() {
    val scoresEkstraliga = repository.getLeagueScores("ekstraliga").asLiveData()
    val scoresPierwszaliga = repository.getLeagueScores("1liga").asLiveData()
    val scoresDrugaliga = repository.getLeagueScores("2liga").asLiveData()

    fun addFavHost(obj: String){
        GlobalScope.async {
            val favTeam1 = FavouriteTeam(name = obj)
            repository.favouriteTeamDao.insert(favTeam1)
        }
    }

    fun addFavGuest(obj: String){
        GlobalScope.async {
            val favTeam1 = FavouriteTeam(name = obj)
            repository.favouriteTeamDao.insert(favTeam1)
        }
    }

    //val message = """[{"score":"59:30","queue":"5","date":"27.06.2021","guests":"MSC W\\u00f6lfe Wittstock","hosts":"Trans MF Landshut Devils","type":"2liga"},{"score":"50:40","queue":"5","date":"26.06.2021","guests":"specHouse PS\\u017b Pozna\\u0144","hosts":"Metalika Recycling Kolejarz Rawicz","type":"2liga"},{"score":"48:42","queue":"4","date":"20.06.2021","guests":"Trans MF Landshut Devils","hosts":"7R Stolaro Stal Rzesz\\u00f3w","type":"2liga"},{"score":"36:54","queue":"4","date":"20.06.2021","guests":"OK Bedmet Kolejarz Opole","hosts":"specHouse PS\\u017b Pozna\\u0144","type":"2liga"},{"score":"36:53","queue":"4","date":"19.06.2021","guests":"Metalika Recycling Kolejarz Rawicz","hosts":"MSC W\\u00f6lfe Wittstock","type":"2liga"},{"score":"69:21","queue":"3","date":"13.06.2021","guests":"MSC W\\u00f6lfe Wittstock","hosts":"OK Bedmet Kolejarz Opole","type":"2liga"},{"score":"49:39","queue":"11","date":"13.06.2021","guests":"7R Stolaro Stal Rzesz\\u00f3w","hosts":"Trans MF Landshut Devils","type":"2liga"},{"score":"0:0","queue":"2","date":"06.06.2021","guests":"OK Bedmet Kolejarz Opole","hosts":"7R Stolaro Stal Rzesz\\u00f3w","type":"2liga"},{"score":"41:49","queue":"2","date":"05.06.2021","guests":"specHouse PS\\u017b Pozna\\u0144","hosts":"MSC W\\u00f6lfe Wittstock","type":"2liga"},{"score":"56:34","queue":"3","date":"30.05.2021","guests":"7R Stolaro Stal Rzesz\\u00f3w","hosts":"specHouse PS\\u017b Pozna\\u0144","type":"2liga"},{"score":"51:38","queue":"3","date":"30.05.2021","guests":"Opibet Lokomotiv Daugavpils","hosts":"OK Bedmet Kolejarz Opole","type":"2liga"},{"score":"48:41","queue":"3","date":"29.05.2021","guests":"Trans MF Landshut Devils","hosts":"Metalika Recycling Kolejarz Rawicz","type":"2liga"},{"score":"42:48","queue":"2","date":"23.05.2021","guests":"Metalika Recycling Kolejarz Rawicz","hosts":"specHouse PS\\u017b Pozna\\u0144","type":"2liga"},{"score":"36:54","queue":"2","date":"21.05.2021","guests":"OK Bedmet Kolejarz Opole","hosts":"Trans MF Landshut Devils","type":"2liga"},{"score":"49:40","queue":"1","date":"16.05.2021","guests":"7R Stolaro Stal Rzesz\\u00f3w","hosts":"Metalika Recycling Kolejarz Rawicz","type":"2liga"},{"score":"55:35","queue":"1","date":"15.05.2021","guests":"specHouse PS\\u017b Pozna\\u0144","hosts":"OK Bedmet Kolejarz Opole","type":"2liga"},{"score":"47:42","queue":"1","date":"08.05.2021","guests":"Trans MF Landshut Devils","hosts":"Opibet Lokomotiv Daugavpils","type":"2liga"}]"""

    private fun readMessage(response: String): List<Score> {
        val learning: List<Score> = Gson().fromJson(response, object : TypeToken<List<Score>>(){}.type)
        learning.forEach {
            it.guests = decode(it.guests)
            it.hosts = decode(it.hosts)
        }
        return learning
    }
    fun getEkstraligaScores(): List<Score> {
        val connection = URL("https://zamknijmor.de/zuzelend/scores/ekstraliga").openConnection() as HttpsURLConnection
        val data = connection.inputStream.bufferedReader().readText()
        return readMessage(data)
    }

    fun getPierwszaLigaScores(): List<Score> {
        val connection = URL("https://zamknijmor.de/zuzelend/scores/1liga").openConnection() as HttpsURLConnection
        val data = connection.inputStream.bufferedReader().readText()
        return readMessage(data)
    }

    fun getDrugaLigaScores(): List<Score> {
        val connection = URL("https://zamknijmor.de/zuzelend/scores/2liga").openConnection() as HttpsURLConnection
        val data = connection.inputStream.bufferedReader().readText()
        return readMessage(data)
    }

    fun decode(`in`: String): String {
        var working = `in`
        var index: Int
        index = working.indexOf("\\u")
        while (index > -1) {
            val length = working.length
            if (index > length - 6) break
            val numStart = index + 2
            val numFinish = numStart + 4
            val substring = working.substring(numStart, numFinish)
            val number = substring.toInt(16)
            val stringStart = working.substring(0, index)
            val stringEnd = working.substring(numFinish)
            working = stringStart + number.toChar() + stringEnd
            index = working.indexOf("\\u")
        }
        return working
    }

    fun updateDb(){
        GlobalScope.async {
            repository.scoreDao.nukeTable()
            // dodajemy wyniki z ekstraligi
            val ekstraligaList = getEkstraligaScores()
            ekstraligaList.forEach{
                repository.scoreDao.insert(it)
            }
            // dodajemy wyniki z pierwszej ligi
            val pierwszaligaList = getPierwszaLigaScores()
            pierwszaligaList.forEach{
                repository.scoreDao.insert(it)
            }
            // dodajemy wyniki z drugiej ligi
            val drugaligaList = getDrugaLigaScores()
            drugaligaList.forEach{
                repository.scoreDao.insert(it)
            }
        }
    }
}