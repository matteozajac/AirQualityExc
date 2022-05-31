package com.matteozajac.airqualityexcercise.logic.repositories

import com.matteozajac.airqualityexcercise.entities.AQStation

interface AQStationsRepository {
    suspend fun getAll(): List<AQStation>
    fun get(id: String): AQStation
}