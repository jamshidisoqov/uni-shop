package io.jamshid.unishop.presentation.feature_main.feature_finance.fragment_income.fragment_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.jamshid.unishop.data.remote.apis.SaleApi
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val saleApi: SaleApi) : ViewModel() {


    fun getProductsByOutPut(outPutId:Long){
        viewModelScope.launch {
            val data = saleApi.getProductsByOutPut(outPutId)
            Timber.d("Products $data")
        }
    }
}