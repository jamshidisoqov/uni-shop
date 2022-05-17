package io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_order

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.jamshid.unishop.data.models.dto.Client
import io.jamshid.unishop.data.models.dto.ClientDto
import io.jamshid.unishop.data.models.dto.OutputDto
import io.jamshid.unishop.data.remote.apis.ClientApi
import io.jamshid.unishop.data.remote.apis.SaleApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val api: ClientApi,
    private val sellApi: SaleApi
) : ViewModel() {

    private var _allClients: MutableStateFlow<List<Client>> = MutableStateFlow(emptyList())
    val allClients: StateFlow<List<Client>> get() = _allClients

    init {
        getAllClient()
    }

    fun addClient(client: ClientDto) {
        viewModelScope.launch {
            api.addClient(
                clientDto = client
            )
        }
    }

    private fun getAllClient() {
        viewModelScope.launch(Dispatchers.IO) {
            _allClients.value = api
                .getAllClients()
        }
    }

    fun addSell(outputDto: OutputDto) {
        Timber.tag(TAG).d("addSell: $outputDto")
        viewModelScope.launch {
            sellApi.addOutput(outputDto)
            Timber.tag(TAG).d("addSell: $outputDto")
        }
    }

}