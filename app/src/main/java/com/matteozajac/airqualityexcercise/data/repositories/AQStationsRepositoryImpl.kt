package com.matteozajac.airqualityexcercise.data.repositories

import com.matteozajac.airqualityexcercise.entities.AQSponsor
import com.matteozajac.airqualityexcercise.entities.AQStation
import com.matteozajac.airqualityexcercise.logic.repositories.AQStationsRepository
import java.lang.IllegalStateException
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
            localDataSource.store(stationsFromRemote)
            stationsFromRemote
        } else {
            localDataSource.getAll()
        }
    }

    override fun get(id: String): AQStation {
        throw  IllegalStateException()
    }
}

interface RemoteAQStationsDataSource {
    fun getAll(): List<AQStation>
}

interface LocalAQStationsDataSource {
    fun getAll(): List<AQStation>
    fun store(stations: List<AQStation>)
}

@Singleton
class AirlyRemoteAQStationsDataSource @Inject constructor() : RemoteAQStationsDataSource {
    override fun getAll(): List<AQStation> {
        return emptyList<AQStation>()
    }
}


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