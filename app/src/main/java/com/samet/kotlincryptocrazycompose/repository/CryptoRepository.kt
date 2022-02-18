package com.samet.kotlincryptocrazycompose.repository

import com.samet.kotlincryptocrazycompose.model.Crypto
import com.samet.kotlincryptocrazycompose.model.CryptoList
import com.samet.kotlincryptocrazycompose.service.CryptoAPI
import com.samet.kotlincryptocrazycompose.util.Constants.API_KEY
import com.samet.kotlincryptocrazycompose.util.Constants.CALL_ATTRIBUTES
import com.samet.kotlincryptocrazycompose.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class CryptoRepository @Inject constructor(
    private val api: CryptoAPI
) {
    suspend fun getCryptoList(): Resource<CryptoList> {
        val Response = try {
            api.getCryptoList(API_KEY)
        } catch (e: Exception) {
            return Resource.Error("Error")
        }

        return Resource.Success(Response)
    }

    suspend fun getCrypto(id: String): Resource<Crypto> {
        val response = try {
            api.getCrypto(API_KEY, id, CALL_ATTRIBUTES)
        } catch (e: Exception) {
            return Resource.Error("Error")
        }

        return Resource.Success(response)
    }
}