package com.matteozajac.airqualityexcercise.data.local
import com.matteozajac.airqualityexcercise.entities.AQStation

interface LocalAQStationsDataSource {
    suspend fun getAll(): List<AQStation>
    fun store(stations: List<AQStation>)
}