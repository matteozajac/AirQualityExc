package com.matteozajac.airqualityexcercise.data.airly
import com.matteozajac.airqualityexcercise.data.remote.RemoteAQStationsDataSource
import com.matteozajac.airqualityexcercise.entities.AQStation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AirlyRemoteAQStationsDataSource @Inject constructor() : RemoteAQStationsDataSource {
    override fun getAll(): List<AQStation> {
        return emptyList<AQStation>()
    }
}