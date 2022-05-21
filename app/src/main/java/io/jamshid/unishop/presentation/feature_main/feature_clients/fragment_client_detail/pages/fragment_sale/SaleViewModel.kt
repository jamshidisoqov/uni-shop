package io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_client_detail.pages.fragment_sale

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.jamshid.unishop.common.Response
import io.jamshid.unishop.data.models.dto.OutputSales
import io.jamshid.unishop.data.remote.apis.ClientApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaleViewModel @Inject constructor(
    private val clientApi: ClientApi
) : ViewModel() {

    private var _allSales: MutableStateFlow<Response<List<OutputSales>>> =
        MutableStateFlow(Response.Loading())
    val allSales: StateFlow<Response<List<OutputSales>>> get() = _allSales

    fun getAllSales(id: Long) {
        viewModelScope.launch {
            try {
                _allSales.emit(Response.Loading())
                val data = clientApi.getSalesByClient(id)
                _allSales.emit(Response.Success(data))
            } catch (e: Exception) {
                _allSales.emit(Response.Error(e.localizedMessage))
            }
        }
    }

}