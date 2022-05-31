package com.matteozajac.airqualityexcercise.data.repositories

import com.matteozajac.airqualityexcercise.entities.AQSponsor
import com.matteozajac.airqualityexcercise.entities.AQStation
import org.junit.Assert.assertTrue
import org.junit.Test

internal class AQStationsRepositoryImplTest {

    @Test fun localReturnsThreeValues_thenRepositoryReturnsThreeValuesWithoutCallingRemote() {

        // Given
        val remoteDS = MockAQStationsRemoteDataSource()
        remoteDS.receivedList = listOf(
            sampleAQStation,
            sampleAQStation,
            sampleAQStation,
            sampleAQStation,
            sampleAQStation
        )

        val localDS = MockLocalDataSource()
        localDS.receivedList = listOf(
            sampleAQStation,
            sampleAQStation,
            sampleAQStation
        )

        val sut = AQStationsRepositoryImpl(
            remoteDataSource = remoteDS,
            localDataSource = localDS
        )

        // When
        val actual = sut.getAll()

        // Then
        assertTrue(actual.count() == 3)
        assertTrue(remoteDS.getAllInvocationsCount == 0)
    }
}

class MockAQStationsRemoteDataSource: RemoteAQStationsDataSource {

    var receivedList: List<AQStation> = emptyList()
    var getAllInvocationsCount: Int = 0

    override fun getAll(): List<AQStation> {
        getAllInvocationsCount++
        return  receivedList
    }
}

class MockLocalDataSource: LocalAQStationsDataSource {
    var receivedList: List<AQStation> = emptyList()

    override fun getAll(): List<AQStation> {
        return  receivedList
    }

    override fun store(stations: List<AQStation>) {
    }

}

val sampleAQStation = AQStation(
    name = "Sample name",
    sponsor = AQSponsor(
        name = "Sample sponsor name",
        logoURL = "Sample logo URL"
    )
)