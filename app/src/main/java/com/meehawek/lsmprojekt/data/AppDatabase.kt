package com.meehawek.lsmprojekt.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.meehawek.lsmprojekt.data.dao.FavouriteTeamDao
import com.meehawek.lsmprojekt.data.dao.ScoreDao
import com.meehawek.lsmprojekt.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.meehawek.lsmprojekt.workers.SeedFavouritesWorker
import com.meehawek.lsmprojekt.workers.SeedScoresWorker

@Database(
    entities = [
        Score::class,
        FavouriteTeam::class
    ], version = 2, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scoreDao(): ScoreDao
    abstract fun favouriteTeamDao() : FavouriteTeamDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .allowMainThreadQueries().addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val request = OneTimeWorkRequestBuilder<SeedFavouritesWorker>().build()
                            WorkManager.getInstance(context).enqueue(request)
                            val request2 = OneTimeWorkRequestBuilder<SeedScoresWorker>().build()
                            WorkManager.getInstance(context).enqueue(request2)
                            GlobalScope.launch(Dispatchers.IO) { rePopulateDb(instance) }
                        }
                    }
                )
                .build()
        }

        suspend fun rePopulateDb(instance: AppDatabase?) {
            instance?.let { db ->
                withContext(Dispatchers.IO) {
                    val scoreDao: ScoreDao = db.scoreDao()
                    val favouriteTeamDao: FavouriteTeamDao = db.favouriteTeamDao()

                    // Hardcoded text for tests
                    val score1 = Score(score = "21:2", queue = 1, date = "21.07.2021", guests = "gorzow", hosts = "zielona_gora", type = "1liga")
                    val score1Id = scoreDao.insert(score1)

                    val fav1 = FavouriteTeam(name = "gorzow")
                    val fav1id = favouriteTeamDao.insert(fav1)
                }
            }
        }
    }
}