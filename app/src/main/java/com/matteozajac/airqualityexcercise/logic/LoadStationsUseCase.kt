package com.matteozajac.airqualityexcercise.logic

import com.matteozajac.airqualityexcercise.entities.AQStation
import kotlinx.coroutines.delay
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

interface LoadStationsUseCase {
    suspend fun execute(): List<AQStation>
}

@Singleton
class LoadStationsInteractor @Inject constructor() : LoadStationsUseCase {
    override suspend fun execute(): List<AQStation> {
      delay(3000)
        return listOf(
            AQStation("Warszawa"),
            AQStation("Krakow Mikoljaksa"),
            AQStation("Wroclaw"),
            AQStation("Gdansk"),
            AQStation("Rzeszow"),
            AQStation("Warszawa"),
            AQStation("Krakow Mikoljaksa"),
            AQStation("Wroclaw"),
            AQStation("Gdansk"),
            AQStation("Rzeszow"),
            AQStation("Warszawa"),
            AQStation("Krakow Mikoljaksa"),
            AQStation("Wroclaw"),
            AQStation("Gdansk"),
            AQStation("Rzeszow"),
            AQStation("Warszawa"),
            AQStation("Krakow Mikoljaksa"),
            AQStation("Wroclaw"),
            AQStation("Gdansk"),
            AQStation("Rzeszow"),
            AQStation("Warszawa"),
            AQStation("Krakow Mikoljaksa"),
            AQStation("Wroclaw"),
            AQStation("Gdansk"),
            AQStation("Rzeszow"),
        )
    }
}