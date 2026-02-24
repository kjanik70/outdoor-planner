package com.outdoorweather.app.di

import com.outdoorweather.app.data.repository.LocationRepositoryImpl
import com.outdoorweather.app.data.repository.WeatherRepositoryImpl
import com.outdoorweather.app.domain.repository.LocationRepository
import com.outdoorweather.app.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

    @Binds
    @Singleton
    abstract fun bindLocationRepository(impl: LocationRepositoryImpl): LocationRepository
}
