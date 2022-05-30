package com.matteozajac.airqualityexcercise.logic

import com.matteozajac.airqualityexcercise.entities.AQStation
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

interface LoadStationsUseCase {
    fun execute(): List<AQStation>
}

@Singleton
class FakeStationsInteractor @Inject constructor() : LoadStationsUseCase {
    override fun execute(): List<AQStation> {
        return emptyList<AQStation>()
    }
}


@Singleton
class LoadStationsInteractor @Inject constructor() : LoadStationsUseCase {
    override fun execute(): List<AQStation> {
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