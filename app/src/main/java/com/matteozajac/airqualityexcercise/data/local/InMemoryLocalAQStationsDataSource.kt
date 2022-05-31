package com.matteozajac.airqualityexcercise.data.local

import com.matteozajac.airqualityexcercise.entities.AQStation
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InMemoryLocalAQStationsDataSource @Inject constructor() : LocalAQStationsDataSource {
    var stations: List<AQStation> = emptyList()

    override suspend fun getAll(): List<AQStation> {
        delay(2000)
        return stations.shuffled()
    }

    override fun store(stations: List<AQStation>) {
        this.stations = stations
    }
}