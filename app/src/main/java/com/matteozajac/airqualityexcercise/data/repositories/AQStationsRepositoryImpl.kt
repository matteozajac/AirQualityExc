package com.matteozajac.airqualityexcercise.data.repositories

import com.matteozajac.airqualityexcercise.data.local.LocalAQStationsDataSource
import com.matteozajac.airqualityexcercise.data.remote.RemoteAQStationsDataSource
import com.matteozajac.airqualityexcercise.entities.AQSponsor
import com.matteozajac.airqualityexcercise.entities.AQStation
import com.matteozajac.airqualityexcercise.logic.repositories.AQStationsRepository
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AQStationsRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteAQStationsDataSource,
    private val localDataSource: LocalAQStationsDataSource,
) : AQStationsRepository {

    override fun getAll(): List<AQStation> {
        return if (localDataSource.getAll().isEmpty()) {
            val stationsFromRemote = remoteDataSource.getAll()
            try {
                localDataSource.store(stationsFromRemote)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            stationsFromRemote
        } else {
            localDataSource.getAll()
        }
    }

    override fun get(id: String): AQStation {
        throw  IllegalStateException()
    }
}

//********************************************************************************************************

@Singleton
class StaticLocalAQStationsDataSource @Inject constructor() : LocalAQStationsDataSource {
    override fun getAll(): List<AQStation> {
        return listOf(
            AQStation(
                name = "Warszawa",
                sponsor = AQSponsor(
                    name = "Jacek",
                    logoURL = ""
                )
            )
        )
    }

    override fun store(stations: List<AQStation>) {
    }
}