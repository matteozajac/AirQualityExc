package com.matteozajac.airqualityexcercise

import com.matteozajac.airqualityexcercise.data.airly.AIRLY_API_BASE_URL
import com.matteozajac.airqualityexcercise.data.airly.AirlyRemoteAQStationsDataSource
import com.matteozajac.airqualityexcercise.data.local.InMemoryLocalAQStationsDataSource
import com.matteozajac.airqualityexcercise.data.local.LocalAQStationsDataSource
import com.matteozajac.airqualityexcercise.data.remote.RemoteAQStationsDataSource
import com.matteozajac.airqualityexcercise.data.repositories.*
import com.matteozajac.airqualityexcercise.logic.*
import com.matteozajac.airqualityexcercise.logic.repositories.AQStationsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AirQualityDIContainer {

    @InstallIn(SingletonComponent::class)
    @Module
    abstract class Logic {
        @Binds
        abstract fun bindLoadStationsUseCase(
            loadStationsImpl: LoadStationsInteractor
        ): LoadStationsUseCase
    }

    @InstallIn(SingletonComponent::class)
    @Module
    abstract class Data {
        @Binds
        abstract fun bindAQStationsRepository(
            aqStationsRepository: AQStationsRepositoryImpl
        ): AQStationsRepository

        @Binds
        abstract fun bindLocalAQStationsDataSource(
            localAQStationsDataSource: InMemoryLocalAQStationsDataSource
        ): LocalAQStationsDataSource


        @Binds
        abstract fun bindRemoteAQStationsDataSource(
            remoteAQStationsDataSource: AirlyRemoteAQStationsDataSource
        ): RemoteAQStationsDataSource


    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AIRLY_API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAirlyAPI(retrofit: Retrofit): AirlyRemoteAQStationsDataSource.AirlyAPI {
        return retrofit.create(AirlyRemoteAQStationsDataSource.AirlyAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideOKHttpClient():OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(FakeAirlyInterceptor())
//            .addInterceptor(AirlyInterceptor())
            .addInterceptor(loggingInterceptor)
            .build()
    }
}

class AirlyInterceptor: Interceptor {
    val apiKey = "LsdA8JBqm6kWfaWg4Q0yFWVo7tIELl5f"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Accept","application/json")
            .addHeader("apikey", apiKey)
            .build()
        return chain.proceed(request)
    }
}

class FakeAirlyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return Response.Builder()
            .code(200)
            .protocol(Protocol.HTTP_2)
            .request(chain.request())
            .message(fakeInstalations)
            .body(fakeInstalations.toByteArray().toResponseBody("application/json".toMediaType()))
            .build()
    }

    val fakeInstalations = """
[{"id":86615,"location":{"latitude":52.231526,"longitude":21.016532},"locationId":44534,"address":{"country":"Polska","city":"Warszawa","street":"Krucza","number":null,"displayAddress1":"Warszawa","displayAddress2":"Biała Podlaska"},"elevation":116.68,"airly":true,"sponsor":{"id":1043,"name":"Centralny Dom Technologii","description":"Airly Sensor's sponsor","logo":"https://cdn.airly.eu/logo/CentralnyDomTechnologii_1632923700726_858481875.PNG","link":"https://cdt.pl/","displayName":"Centralny Dom Technologii"}},{"id":87285,"location":{"latitude":52.223896,"longitude":21.017904},"locationId":9944,"address":{"country":"Polska","city":"Warszawa","street":"Piękna","number":"24/26","displayAddress1":"Warszawa","displayAddress2":"Piękna"},"elevation":116.72,"airly":true,"sponsor":{"id":767,"name":"Huawei","description":"Airly Sensor's sponsor","logo":"https://cdn.airly.eu/logo/Huawei_1601292158328_559620620.jpg","link":null,"displayName":"Huawei"}},{"id":6864,"location":{"latitude":52.227118,"longitude":21.024308},"locationId":6864,"address":{"country":"Polska","city":"Warszawa","street":"Wiejska","number":"19","displayAddress1":"Warszawa","displayAddress2":"Wiejska"},"elevation":111.24,"airly":true,"sponsor":{"id":49,"name":"Anonimowy","description":"Airly Sensor's sponsor","logo":"https://cdn.airly.eu/logo/Anonimowy_1602689776821_829611598.png","link":null,"displayName":"Anonimowy"}},{"id":98399,"location":{"latitude":52.237714,"longitude":21.013915},"locationId":52858,"address":{"country":"Polska","city":"Warszawa","street":"Dowcip","number":"4","displayAddress1":"Warszawa","displayAddress2":"Dowcip"},"elevation":114.87,"airly":true,"sponsor":{"id":1164,"name":"Mimatech","description":"Airly Sensor's sponsor","logo":"https://cdn.airly.eu/logo/Mimatech_1652903674844_1915467061.png","link":"http://mimatech.pl/","displayName":"Mimatech"}},{"id":9064,"location":{"latitude":52.221714,"longitude":21.009337},"locationId":9064,"address":{"country":"Polska","city":"Warszawa","street":"Plac Politechniki","number":"1","displayAddress1":"Warszawa","displayAddress2":"Plac Politechniki"},"elevation":119.33,"airly":true,"sponsor":{"id":22,"name":"Aviva","description":"Airly Sensor's sponsor","logo":"https://cdn.airly.eu/logo/Aviva_1538146740542_399306786.jpg","link":"https://wiemczymoddycham.pl/","displayName":"Aviva"}}]
    """.trimIndent()
}