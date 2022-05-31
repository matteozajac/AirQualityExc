package com.matteozajac.airqualityexcercise.data.airly

import com.matteozajac.airqualityexcercise.AirQualityDIContainer
import com.matteozajac.airqualityexcercise.IoDispatcher
import com.matteozajac.airqualityexcercise.data.remote.RemoteAQStationsDataSource
import com.matteozajac.airqualityexcercise.entities.AQSponsor
import com.matteozajac.airqualityexcercise.entities.AQStation
import com.matteozajac.airqualityexcercise.entities.CAQI
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AirlyRemoteAQStationsDataSource @Inject constructor(
    private val airlyAPI: AirlyAPI,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : RemoteAQStationsDataSource {

    private val airlyAPIMapper = AirlyAPIMapper()

    override suspend fun getAll(): List<AQStation> = withContext(dispatcher) {
        val installationDTOs = airlyAPI.getInstalations()
        val measurements: MutableMap<Long, AirlyDTO.Measurement> = mutableMapOf()

        installationDTOs.forEach {
            val measurement = airlyAPI.getMeasurements(it.id)
            measurements[it.id] = measurement
        }

        return@withContext airlyAPIMapper.map(installationDTOs, measurements)
    }

    interface AirlyAPI {
        @GET("installations/nearest?lat=52.2297&lng=21.0122&maxDistanceKM=50&maxResults=2")
        suspend fun getInstalations(): List<AirlyDTO.Instalation>

        @GET("measurements/installation")
        suspend fun getMeasurements(@Query("installationId") id: Long): AirlyDTO.Measurement
    }
}

class AirlyAPIMapper {
    fun map(
        installations: List<AirlyDTO.Instalation>,
        measurements: Map<Long, AirlyDTO.Measurement>
    ): List<AQStation> {
        return installations.map { installation ->
            val measurement = measurements[installation.id]
            map(installation = installation, measurement = measurement)
        }
    }

    fun map(
        installation: AirlyDTO.Instalation,
        measurement: AirlyDTO.Measurement?
    ): AQStation {
        val firstMeasurement = measurement?.current?.indexes?.first()
        return AQStation(
            name = "${installation.address.displayAddress1} ${installation.address.displayAddress2} ",
            sponsor = AQSponsor(
                name = installation.sponsor.displayName,
                logoURL = installation.sponsor.logo
            ),
            caqi = CAQI(
                value = firstMeasurement?.value ?: 0.0,
                description = firstMeasurement?.description + " " +firstMeasurement?.advice,
                hexColor = firstMeasurement?.color ?: "#101010"
            )
        )
    }
}

val AIRLY_API_BASE_URL = "https://airapi.airly.eu/v2/"