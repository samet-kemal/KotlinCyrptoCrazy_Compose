package com.samet.kotlincryptocrazycompose.dependencyInjection

import com.samet.kotlincryptocrazycompose.repository.CryptoRepository
import com.samet.kotlincryptocrazycompose.service.CryptoAPI
import com.samet.kotlincryptocrazycompose.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCryptoRepository(
        api: CryptoAPI
    ) = CryptoRepository(api)


    @Singleton
    @Provides
    fun provideCryptoApi(): CryptoAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(CryptoAPI::class.java)

    }


}