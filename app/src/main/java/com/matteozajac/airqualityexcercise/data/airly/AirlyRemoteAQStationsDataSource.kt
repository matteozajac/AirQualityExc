package com.matteozajac.airqualityexcercise.data.airly

import com.matteozajac.airqualityexcercise.data.remote.RemoteAQStationsDataSource
import com.matteozajac.airqualityexcercise.entities.AQSponsor
import com.matteozajac.airqualityexcercise.entities.AQStation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.http.GET
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AirlyRemoteAQStationsDataSource @Inject constructor(
    private val airlyAPI: AirlyAPI
) : RemoteAQStationsDataSource {

    private val airlyAPIMapper = AirlyAPIMapper()

    override suspend fun getAll(): List<AQStation> = withContext(Dispatchers.IO) {
        val installationDTOs = airlyAPI.getInstalations()
        return@withContext airlyAPIMapper.map(installationDTOs)
    }

    interface AirlyAPI {
        @GET("installations/nearest?lat=52.2297&lng=21.0122&maxDistanceKM=50&maxResults=50")
        suspend fun getInstalations(): List<AirlyDTO.Instalation>
    }
}

class AirlyAPIMapper {
    fun map(installations: List<AirlyDTO.Instalation>): List<AQStation> {
        return installations.map { map(installation = it) }
    }

    fun map(installation: AirlyDTO.Instalation): AQStation {
        return AQStation(
            name = "${installation.address.displayAddress1} ${installation.address.displayAddress2} ",
            sponsor = AQSponsor(
                name = installation.sponsor.displayName,
                logoURL = installation.sponsor.logo
            )
        )
    }
}

val AIRLY_API_BASE_URL = "https://airapi.airly.eu/v2/"