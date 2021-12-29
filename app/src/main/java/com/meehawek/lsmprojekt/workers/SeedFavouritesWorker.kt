package com.meehawek.lsmprojekt.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.meehawek.lsmprojekt.data.AppDatabase
import com.meehawek.lsmprojekt.data.FavouriteTeam
import com.meehawek.lsmprojekt.utils.*
import kotlinx.coroutines.coroutineScope

class SeedFavouritesWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open(FAVOURITES_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val plantType = object : TypeToken<List<FavouriteTeam>>() {}.type
                    val learning: List<FavouriteTeam> = Gson().fromJson(jsonReader, plantType)

                    val database = AppDatabase.getInstance(applicationContext)
                    learning.forEach {
                        database.favouriteTeamDao().insert(it)
                    }
                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "SeedDatabaseWorker"
    }
}