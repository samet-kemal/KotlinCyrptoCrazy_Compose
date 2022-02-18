package com.samet.kotlincryptocrazycompose.viewModel

import androidx.lifecycle.ViewModel
import com.samet.kotlincryptocrazycompose.model.Crypto
import com.samet.kotlincryptocrazycompose.repository.CryptoRepository
import com.samet.kotlincryptocrazycompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CryptoDetailViewModel @Inject constructor(
    private val repository: CryptoRepository
) : ViewModel() {


    /**----ViewModelScope YErine Suspend Fun Konuldu*/
    suspend fun getCrypto(id: String): Resource<Crypto> {

        return repository.getCrypto(id)

    }


}