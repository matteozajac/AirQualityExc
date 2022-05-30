package com.matteozajac.airqualityexcercise.logic

import com.matteozajac.airqualityexcercise.entities.AQStation

class LoadStationsUseCase {
    fun execute(): List<AQStation> {
        return listOf(AQStation("Warszawa"))
    }
}