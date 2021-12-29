package com.meehawek.lsmprojekt.di

import android.content.Context
import com.meehawek.lsmprojekt.data.AppDatabase
import com.meehawek.lsmprojekt.data.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideSubjectDao(appDatabase: AppDatabase): ScoreDao {
        return appDatabase.scoreDao()
    }

    @Provides
    fun provideFavouriteTeamDao(appDatabase: AppDatabase): FavouriteTeamDao {
        return appDatabase.favouriteTeamDao()
    }
}