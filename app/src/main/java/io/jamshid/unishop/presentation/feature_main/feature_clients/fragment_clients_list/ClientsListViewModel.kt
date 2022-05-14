package io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_clients_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.jamshid.unishop.data.models.dto.ClientDto
import io.jamshid.unishop.data.remote.apis.ClientApi
import io.jamshid.unishop.domain.models.Client
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientsListViewModel @Inject constructor(
    private val clientApi: ClientApi
) : ViewModel() {

    private val _allClients = MutableStateFlow<List<Client>>(emptyList())
    val allClients = _allClients.asStateFlow()

    init {
        viewModelScope.launch {
            _allClients.emit(
                clientApi.getAllClients()
            )
        }
    }

    fun addClient(clientDto: ClientDto) {
        fun addClient(client: ClientDto) {
            viewModelScope.launch {
                clientApi.addClient(
                    clientDto = client
                )
            }
        }
    }
}
