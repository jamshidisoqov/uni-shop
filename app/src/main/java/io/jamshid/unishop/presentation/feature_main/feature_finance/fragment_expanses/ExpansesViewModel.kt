package io.jamshid.unishop.presentation.feature_main.feature_finance.fragment_expanses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.jamshid.unishop.common.Response
import io.jamshid.unishop.data.models.dto.ExpansesDto
import io.jamshid.unishop.data.remote.apis.SaleApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpansesViewModel @Inject constructor(
    private val saleApi: SaleApi
) : ViewModel() {


    private var _allExpanses: MutableStateFlow<Response<List<ExpansesDto>>> =
        MutableStateFlow(Response.Default())
    val allExpanses: StateFlow<Response<List<ExpansesDto>>> get() = _allExpanses

    init {
        getAllExpanses()
    }

    private fun getAllExpanses() {
        viewModelScope.launch {
            try {
                _allExpanses.emit(Response.Loading())
                val data = saleApi.getAllExpanses()
                _allExpanses.emit(Response.Success(data))
            } catch (e: Exception) {
                _allExpanses.emit(Response.Error(e.localizedMessage!!.toString()))
            }
        }
    }

    fun getExpansesByDate(first: Long, second: Long) {
        viewModelScope.launch {
            try {
                _allExpanses.emit(Response.Loading())
                val data = saleApi.getExpansesByDate(first, second)
                _allExpanses.emit(Response.Success(data))
            } catch (e: Exception) {
                _allExpanses.emit(Response.Error(e.localizedMessage!!.toString()))
            }
        }
    }

}