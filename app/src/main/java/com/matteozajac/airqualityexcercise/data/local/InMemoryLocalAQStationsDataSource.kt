package com.matteozajac.airqualityexcercise.data.local

import com.matteozajac.airqualityexcercise.entities.AQStation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InMemoryLocalAQStationsDataSource @Inject constructor() : LocalAQStationsDataSource {
    var stations: List<AQStation> = emptyList()

    override fun getAll(): List<AQStation> {
        return stations.shuffled()
    }

    override fun store(stations: List<AQStation>) {
        this.stations = stations
    }
}