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

    override suspend fun getAll(): List<AQStation> = withContext(Dispatchers.IO) {
        return@withContext airlyAPI.getInstalations().map { instalation ->
            AQStation(
                name = "${instalation.address.displayAddress1} ${instalation.address.displayAddress2} ",
                sponsor = AQSponsor(
                    name = instalation.sponsor.displayName,
                    logoURL = instalation.sponsor.logo
                )
            )
        }
    }

    interface AirlyAPI {
        @GET("installations/nearest?lat=52.2297&lng=21.0122&maxDistanceKM=50&maxResults=5")
        suspend fun getInstalations(): List<AirlyDTO.Instalation>
    }
}

val AIRLY_API_BASE_URL = "https://airapi.airly.eu/v2/"