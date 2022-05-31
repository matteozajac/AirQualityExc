package com.matteozajac.airqualityexcercise.data.airly

import com.google.gson.annotations.SerializedName

class AirlyDTO {
    data class Instalation(
        val id: Long,
        val location: Location,

        @SerializedName("locationId")
        val locationID: Long,

        val address: Address,
        val elevation: Double,
        val airly: Boolean,
        val sponsor: Sponsor
    )

    data class Address(
        val country: String,
        val city: String,
        val street: String,
        val number: String? = null,
        val displayAddress1: String,
        val displayAddress2: String
    )

    data class Location(
        val latitude: Double,
        val longitude: Double
    )

    data class Sponsor(
        val id: Long,
        val name: String,
        val description: String,
        val logo: String,
        val link: String,
        val displayName: String
    )

    data class Measurement(
        val current: CurrentMeasurement
    )

    data class CurrentMeasurement(
        val indexes: List<MeasurementIndex>
    )

    data class MeasurementIndex(
        val value: Double,
        val description: String,
        val advice: String,
        val color: String
    )
}
