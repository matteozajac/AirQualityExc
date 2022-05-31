package com.matteozajac.airqualityexcercise

import com.matteozajac.airqualityexcercise.entities.AQStation
import com.matteozajac.airqualityexcercise.logic.LoadStationsUseCase
import javax.inject.Inject
import javax.inject.Singleton


interface AnalyticService {
    fun log(key: String, value: String)
}

class DebugAnalyticService : AnalyticService {
    override fun log(key: String, value: String) {
        println("$key: $value")
    }
}

class AEMAnalyticService : AnalyticService {
    override fun log(key: String, value: String) {
        println("Sending to AEM $key: $value")
    }

}

class FirebaseAnalyticService : AnalyticService {
    //    val firebase: Firebase
    override fun log(key: String, value: String) {
        //    firebase.logEvent(...)
        println("Sending to Firebase $key: $value")
    }
}

class AQStationAnalyticService : AnalyticService {
    private val services: List<AnalyticService> = listOf(
        FirebaseAnalyticService(),
        AEMAnalyticService(),
        DebugAnalyticService()
    )

    override fun log(key: String, value: String) {
        services.forEach {
            it.log(key = "SomeKey", value = "Other value")
        }
    }
}

@Singleton
class AnalyticsLoadStationsInteractor @Inject constructor(
    private val useCase: LoadStationsUseCase,
    private val analyticService: AnalyticService
) : LoadStationsUseCase {
    override suspend fun execute(): List<AQStation> {
        analyticService.log(key = "SomeKey", value = "Other value")
        return useCase.execute()
    }
}