package com.matteozajac.airqualityexcercise.entities

data class AQStation(
    val name: String,
    val sponsor: AQSponsor,
    val caqi: CAQI
)

data class AQSponsor(
    val name: String,
    val logoURL: String
)

data class CAQI(
    val value: Double,
    val description: String,
    val hexColor: String
)