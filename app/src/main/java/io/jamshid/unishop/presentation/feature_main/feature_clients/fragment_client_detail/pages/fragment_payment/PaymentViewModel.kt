package io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_client_detail.pages.fragment_payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.jamshid.unishop.common.Response
import io.jamshid.unishop.data.models.dto.PaymentHistory
import io.jamshid.unishop.data.remote.apis.ClientApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(private val clientApi: ClientApi) : ViewModel() {

    private var _allPayments: MutableStateFlow<Response<List<PaymentHistory>>> =
        MutableStateFlow(Response.Loading())
    val allPayments: StateFlow<Response<List<PaymentHistory>>> get() = _allPayments

    fun getAllPayments(id: Long) {
        viewModelScope.launch {
            try {
                _allPayments.emit(Response.Loading())
                val data = clientApi.getHistoryByClient(id)
                _allPayments.emit(Response.Success(data))
            } catch (e: Exception) {
                _allPayments.emit(Response.Error(e.localizedMessage))
            }
        }
    }

}