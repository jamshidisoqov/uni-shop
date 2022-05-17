package io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_client_detail.pages.fragment_sale

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.jamshid.unishop.common.Constants.CLIENT_DETAIL
import io.jamshid.unishop.data.models.dto.Client
import io.jamshid.unishop.data.models.dto.OutputSales
import io.jamshid.unishop.data.remote.apis.SaleApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaleViewModel @Inject constructor(
    private val saleApi: SaleApi,
    private val handle: SavedStateHandle
) : ViewModel() {

    private val _salesState = MutableStateFlow<List<OutputSales>>(emptyList())
    val salesState: StateFlow<List<OutputSales>> = _salesState.asStateFlow()


    init {
        viewModelScope.launch {
            val client = handle.get<Client>(CLIENT_DETAIL)
            _salesState.value = saleApi.getSalesByClientId(client?.id ?: return@launch)
        }
    }
}