package com.matteozajac.airqualityexcercise.data.remote
import com.matteozajac.airqualityexcercise.entities.AQStation

interface RemoteAQStationsDataSource {
    fun getAll(): List<AQStation>
}