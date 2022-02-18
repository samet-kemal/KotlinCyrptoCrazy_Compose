package com.samet.kotlincryptocrazycompose.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samet.kotlincryptocrazycompose.model.CryptoListItem
import com.samet.kotlincryptocrazycompose.repository.CryptoRepository
import com.samet.kotlincryptocrazycompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoListViewModel @Inject constructor(
    private val repository: CryptoRepository
) : ViewModel() {

    var cryptoList = mutableStateOf<List<CryptoListItem>>(listOf())
    var errorMesage = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    private var initialCryptoList = listOf<CryptoListItem>()
    private var isSearchStarting = true


    init {
        loadCryptos()
    }

    fun searchCryptoList(query: String) {
        val listToSearch = if (isSearchStarting) {
            cryptoList.value
        } else {
            initialCryptoList
        }

        viewModelScope.launch(Dispatchers.Default) {

            if (query.isEmpty()) {
                cryptoList.value = initialCryptoList
                isSearchStarting = true
                return@launch
            } else {
                val results = listToSearch.filter {
                    it.currency.contains(query.trim(), ignoreCase = true)
                }

                if (isSearchStarting) {
                    initialCryptoList = cryptoList.value
                    isSearchStarting = false
                }

                cryptoList.value = results

            }

        }

    }


    /**-----İKİ YOLLA YAPILABİLİR
     * 1)FONKSİYON SUSPEND LARAK TANIMLANABİLİR
     * 2) VİEWMODELSCOPE:LAUNCH KULLANILIR
     * -----*/

    fun loadCryptos() {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getCryptoList()
            when (result) {
                is Resource.Success -> {

                    val cryptoItems = result.data!!.mapIndexed { index, cryptoListItem ->
                        CryptoListItem(cryptoListItem.currency, cryptoListItem.price)
                    }

                    errorMesage.value = ""
                    isLoading.value = false
                    cryptoList.value += cryptoItems


                }
                is Resource.Error -> {
                    errorMesage.value = result.message!!
                    isLoading.value = false

                }
            }


        }


    }


}