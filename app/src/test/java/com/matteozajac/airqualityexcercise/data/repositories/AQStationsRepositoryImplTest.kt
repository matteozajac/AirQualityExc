package com.matteozajac.airqualityexcercise.data.repositories

import com.matteozajac.airqualityexcercise.data.local.LocalAQStationsDataSource
import com.matteozajac.airqualityexcercise.data.remote.RemoteAQStationsDataSource
import com.matteozajac.airqualityexcercise.entities.AQSponsor
import com.matteozajac.airqualityexcercise.entities.AQStation
import org.junit.Assert.assertTrue
import org.junit.Test

internal class AQStationsRepositoryImplTest {

    @Test
    suspend fun localReturnsThreeValues_thenRepositoryReturnsThreeValuesWithoutCallingRemote() {

        // Given
        val remoteDS = MockAQStationsRemoteDataSource()
        remoteDS.receivedList = MutableList(5) { sampleAQStation}

        val localDS = MockLocalDataSource()
        localDS.receivedList = MutableList(3) { sampleAQStation }

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

    @Test
    suspend fun localReturnsZeroValuesAndRemoteReturnFiveValues_thenRepositoryReturnsFiveValues() {
        //Given
        val remoteDS = MockAQStationsRemoteDataSource()
        remoteDS.receivedList = MutableList(5) { sampleAQStation}

        val localDS = MockLocalDataSource()
        localDS.receivedList = emptyList()

        val sut = AQStationsRepositoryImpl(
            remoteDataSource = remoteDS,
            localDataSource = localDS
        )

        //When
        val actual = sut.getAll()

        //Then
        assertTrue(actual.count() == 5)
        assertTrue(remoteDS.getAllInvocationsCount == 1)
        assertTrue(localDS.getAllInvocationsCount == 1)
    }

    @Test
    suspend fun localDStoreThrowsError() {
        //Given remote return five values
        val remoteDS = MockAQStationsRemoteDataSource()
        remoteDS.receivedList = MutableList(5) { sampleAQStation}

        //And local throws an error during storing data
        val localDS = MockLocalDataSource()
        localDS.receivedList = emptyList()
        localDS.storeException = IllegalStateException()

        val sut = AQStationsRepositoryImpl(
            remoteDataSource = remoteDS,
            localDataSource = localDS
        )

        //When calling getAll() on repository
        val actual = sut.getAll()

        //Then repository return five values
        assertTrue(actual.count() == 5)
        assertTrue(localDS.storeInvocationCount == 1)
        assertTrue(remoteDS.getAllInvocationsCount == 1)
        assertTrue(localDS.getAllInvocationsCount == 1)
    }
}

class MockAQStationsRemoteDataSource: RemoteAQStationsDataSource {

    var receivedList: List<AQStation> = emptyList()
    var getAllInvocationsCount: Int = 0

    override suspend fun getAll(): List<AQStation> {
        getAllInvocationsCount++
        return  receivedList
    }
}

class MockLocalDataSource: LocalAQStationsDataSource {
    var receivedList: List<AQStation> = emptyList()
    var getAllInvocationsCount: Int = 0

    override fun getAll(): List<AQStation> {
        getAllInvocationsCount++
        return  receivedList
    }

    var storeInvocationCount: Int = 0
    var storeException: Exception? = null
    override fun store(stations: List<AQStation>) {
        storeInvocationCount++
        storeException?.let { throw it }
    }
}

val sampleAQStation = AQStation(
    name = "Sample name",
    sponsor = AQSponsor(
        name = "Sample sponsor name",
        logoURL = "Sample logo URL"
    )
)