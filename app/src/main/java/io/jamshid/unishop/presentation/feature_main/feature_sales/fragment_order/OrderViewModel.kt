package io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_order

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
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val api: ClientApi,
    private val sellApi: SaleApi
) : ViewModel() {

    private var _allClients: MutableStateFlow<List<Client>> = MutableStateFlow(emptyList())
    val allClients: StateFlow<List<Client>> get() = _allClients

    var addSalesStatus: MutableStateFlow<Int> = MutableStateFlow(400)

    init {
        getAllClient()
    }

    fun addClient(client: ClientDto) {
        viewModelScope.launch {
            api.addClient(
                clientDto = client
            )
            getAllClient()
        }
    }

    private fun getAllClient() {
        viewModelScope.launch(Dispatchers.IO) {
            _allClients.value = api
                .getAllClients()
        }
    }

    fun addSell(outputDto: OutputDto) {
        viewModelScope.launch {
            addSalesStatus.emit(sellApi.addOutput(outputDto).status)
        }
    }

}