package com.matteozajac.airqualityexcercise.entities

data class AQStation(
    val name: String,
    val sponsor: AQSponsor
)

data class AQSponsor(
    val name: String,
    val logoURL: String
)