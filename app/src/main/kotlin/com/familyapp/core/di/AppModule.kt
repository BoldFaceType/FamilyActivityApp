package com.familyapp.core.di

import android.content.Context
import com.familyapp.core.config.AppConfig
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.InputStreamReader
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppConfig(@ApplicationContext context: Context): AppConfig {
        val assetManager = context.assets
        val inputStream = assetManager.open("config.json") // Assumes config.json is in assets
        val reader = InputStreamReader(inputStream)
        return Gson().fromJson(reader, AppConfig::class.java)
    }
}
