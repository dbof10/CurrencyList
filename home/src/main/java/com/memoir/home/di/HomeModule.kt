package com.memoir.home.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.memoir.home.repo.CryptoDatabase
import com.memoir.home.repo.HomeRepository
import com.memoir.home.repo.MockCryptoApiClient
import com.memoir.home.ui.CurrencyAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object HomeModule {

    @Provides
    @ActivityRetainedScoped
    fun provideHomeRepo(db: CryptoDatabase, gson: Gson): HomeRepository {
        return HomeRepository(MockCryptoApiClient(gson), db.currencyDao())
    }


    @Provides
    fun providePropertyDatabase(@ApplicationContext context: Context): CryptoDatabase {
        return Room.databaseBuilder(
            context,
            CryptoDatabase::class.java, "crypto_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

}