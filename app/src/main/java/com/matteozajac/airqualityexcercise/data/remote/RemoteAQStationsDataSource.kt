package com.matteozajac.airqualityexcercise.data.remote
import com.matteozajac.airqualityexcercise.entities.AQStation

interface RemoteAQStationsDataSource {
    suspend fun getAll(): List<AQStation>
}