package io.jamshid.unishop.presentation.feature_main.feature_finance.fragment_expanses.fragment_add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.jamshid.unishop.data.models.dto.ExpansesDto
import io.jamshid.unishop.data.remote.apis.SaleApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddExpansesViewModel @Inject constructor(
    private val saleApi: SaleApi
) : ViewModel() {

    var expanses: MutableStateFlow<Int> = MutableStateFlow(0)

    fun addExpanses(expansesDto: ExpansesDto) {
        viewModelScope.launch {
            expanses.emit(saleApi.addExpanses(expansesDto).status)
        }
    }

}