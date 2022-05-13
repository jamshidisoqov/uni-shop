package io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_sales

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.jamshid.unishop.common.Response
import io.jamshid.unishop.data.remote.apis.ProductApi
import io.jamshid.unishop.domain.models.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SalesViewModel @Inject constructor(
    private val productApi: ProductApi
) : ViewModel() {

    private var _allProducts = MutableStateFlow<Response<List<Product>>>(Response.Default())
    val allProducts = _allProducts.asStateFlow()

    init { getAllProducts() }

    private fun getAllProducts() {
        viewModelScope.launch {
            try {
                _allProducts.emit(Response.Loading())
                val data = productApi.getAllProduct()
                _allProducts.emit(Response.Success(data = data.map { it.toProduct() }))
            } catch (e: Exception) {
                _allProducts.emit(Response.Error(e.localizedMessage!!.toString()))
            }
        }
    }
}