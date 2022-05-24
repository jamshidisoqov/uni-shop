package io.jamshid.unishop.presentation.feature_main.feature_finance.fragment_income

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.jamshid.unishop.common.Response
import io.jamshid.unishop.data.models.dto.OutputSales
import io.jamshid.unishop.data.remote.apis.SaleApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class IncomeViewModel @Inject constructor(private val saleApi: SaleApi) : ViewModel() {

    private var _allSales: MutableStateFlow<Response<List<OutputSales>>> =
        MutableStateFlow(Response.Loading())
    val allSales: StateFlow<Response<List<OutputSales>>> get() = _allSales


    init {
        getAllSales()
    }

    fun getAllSales() {
        Timber.d("allSales: keldi")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _allSales.emit(Response.Loading())
                val data = saleApi.getAllSales()
                Timber.d("allSales: $data")
                _allSales.emit(Response.Success(data))
            } catch (e: Exception) {
                Timber.d("allSales: error")
                _allSales.emit(Response.Error(e.localizedMessage))
            }
        }
    }

    fun sortedFromDate(start: Long, end: Long) {
        viewModelScope.launch {
            try {
                _allSales.emit(Response.Loading())
                val data = saleApi.getSalesByDate(start, end)
                _allSales.emit(Response.Success(data))
            } catch (e: Exception) {
                _allSales.emit(Response.Error(e.localizedMessage!!.toString()))
            }
        }
    }
}