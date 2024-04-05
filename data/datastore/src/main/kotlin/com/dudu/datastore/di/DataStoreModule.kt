package com.dudu.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.dudu.data.datastore.WeatherPreferences
import com.dudu.datastore.WeatherPreferencesSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    internal fun providesWeatherPlacePreferencesDataStore(
        @ApplicationContext context: Context,
        weatherPreferencesSerializer: WeatherPreferencesSerializer
    ): DataStore<WeatherPreferences> =
        DataStoreFactory.create(
            serializer = weatherPreferencesSerializer
        ) {
            context.dataStoreFile("weather_preferences.pb")
        }

}