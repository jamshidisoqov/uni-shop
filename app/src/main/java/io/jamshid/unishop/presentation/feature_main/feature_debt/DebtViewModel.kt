package io.jamshid.unishop.presentation.feature_main.feature_debt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.jamshid.unishop.common.Response
import io.jamshid.unishop.data.models.dto.OutputSales
import io.jamshid.unishop.data.remote.apis.DebtApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DebtViewModel @Inject constructor(
    private var debtApi: DebtApi
) : ViewModel() {


    private var _allDebt: MutableStateFlow<Response<List<OutputSales>>> =
        MutableStateFlow(Response.Default())
    val allDebt: StateFlow<Response<List<OutputSales>>> get() = _allDebt


    fun getAllDebt() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _allDebt.emit(Response.Loading())
                val data = debtApi.getAllDebt()
                _allDebt.emit(Response.Success(data))
            } catch (e: Exception) {
                _allDebt.emit(Response.Error(e.localizedMessage!!.toString()))
            }
        }
    }



    fun searchDebt(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _allDebt.emit(Response.Loading())
                val data = debtApi.searchDebt(name)
                _allDebt.emit(Response.Success(data))
            } catch (e: Exception) {
                _allDebt.emit(Response.Error(e.localizedMessage!!.toString()))
            }
        }
    }

}