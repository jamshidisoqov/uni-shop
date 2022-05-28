package io.jamshid.unishop.presentation.feature_main.feature_finance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.jamshid.unishop.common.Response
import io.jamshid.unishop.data.remote.apis.SaleApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FinanceViewModel @Inject constructor(
    private val saleApi: SaleApi
) : ViewModel() {




    private var _lastSevenBalance: MutableStateFlow<Response<List<Float>>> =
        MutableStateFlow(Response.Loading())
    val lastSevenBalance: StateFlow<Response<List<Float>>> get() = _lastSevenBalance


    fun getLastSevenBalance() {
        viewModelScope.launch {
            try {
                _lastSevenBalance.emit(Response.Loading())
                val date = saleApi.getLastSevenMonth()
                val list = ArrayList<Float>()
                date.forEach {
                    if (it == null) {
                        list.add(0.0f)
                    } else {
                        list.add(it.toFloat())
                    }
                }

                _lastSevenBalance.emit(Response.Success(list))
            } catch (e: Exception) {
                _lastSevenBalance.emit(Response.Error(e.localizedMessage!!.toString()))
            }
        }
    }


}