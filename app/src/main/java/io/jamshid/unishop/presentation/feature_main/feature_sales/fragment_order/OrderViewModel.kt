package io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.jamshid.unishop.data.remote.apis.ClientApi
import io.jamshid.unishop.domain.models.Client
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val api: ClientApi
) : ViewModel() {

    private var _allClients: MutableStateFlow<List<Client>> = MutableStateFlow(emptyList())
    val allClients: StateFlow<List<Client>> get() = _allClients

    init {
        getAllClient()
    }

    fun addClient(client: Client) {
        viewModelScope.launch {
            api.addClient(
                clientDto = client.toClientDto()
            )
        }
    }

    fun getAllClient() {
        viewModelScope.launch {
            _allClients.value = api
                .getAllClients()
                .map { it.toClient() }
        }
    }

//    fun addSell(outputDto: OutputDto) {
//        viewModelScope.launch {
//
//        }
//    }

}