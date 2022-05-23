package io.jamshid.unishop.presentation.feature_main.feature_debt.fragment_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.jamshid.unishop.common.Response
import io.jamshid.unishop.data.models.OutputPayment
import io.jamshid.unishop.data.models.dto.PaymentHistory
import io.jamshid.unishop.data.remote.apis.DebtApi
import io.jamshid.unishop.domain.models.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DebtDetailsViewModel @Inject constructor(
    private var debtApi: DebtApi
) : ViewModel() {


    private var _allProductsByOutput: MutableStateFlow<Response<List<Product>>> =
        MutableStateFlow(Response.Loading())
    val allProductsByOutput: StateFlow<Response<List<Product>>> get() = _allProductsByOutput


    private var _allPaymentsByOutput: MutableStateFlow<Response<List<PaymentHistory>>> =
        MutableStateFlow(Response.Loading())
    val allPaymentsByOutput: StateFlow<Response<List<PaymentHistory>>> get() = _allPaymentsByOutput

    fun allProducts(id: Long) {
        viewModelScope.launch {
            try {
                _allProductsByOutput.emit(Response.Loading())
                val data = debtApi.getAllProductByOutput(id)
                _allProductsByOutput.emit(Response.Success(data))

            } catch (e: Exception) {
                _allProductsByOutput.emit(Response.Error(e.localizedMessage!!.toString()))
            }
        }
    }

    fun allPayments(id: Long) {
        viewModelScope.launch {

        }
    }

    fun paymentForOutput(payment: OutputPayment) {
        viewModelScope.launch {

        }
    }


}