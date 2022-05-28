package io.jamshid.unishop.presentation.feature_main.feature_debt.fragment_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.jamshid.unishop.common.Response
import io.jamshid.unishop.data.models.OutputPayment
import io.jamshid.unishop.data.models.dto.OutputProduct
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


    private var _allProductsByOutput: MutableStateFlow<Response<List<OutputProduct>>> =
        MutableStateFlow(Response.Loading())
    val allProductsByOutput: StateFlow<Response<List<OutputProduct>>> get() = _allProductsByOutput





    private var _allPaymentsByOutput: MutableStateFlow<Response<List<PaymentHistory>>> =
        MutableStateFlow(Response.Loading())
    val allPaymentsByOutput: StateFlow<Response<List<PaymentHistory>>> get() = _allPaymentsByOutput

    var status:MutableStateFlow<Int> = MutableStateFlow(400)

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
            try {
                _allPaymentsByOutput.emit(Response.Loading())
                val data = debtApi.getAllPaymentsBYOutput(id)
                _allPaymentsByOutput.emit(Response.Success(data))
            } catch (e: Exception) {
                _allPaymentsByOutput.emit(Response.Error(e.localizedMessage!!.toString()))
            }
        }
    }

    fun paymentForOutput(payment: OutputPayment) {
        viewModelScope.launch {
            status.emit(debtApi.newPayment(payment).status)
        }
    }


}