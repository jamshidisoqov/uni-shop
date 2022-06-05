package io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_sales

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.jamshid.unishop.common.Response
import io.jamshid.unishop.data.remote.apis.ProductApi
import io.jamshid.unishop.domain.models.Product
import io.jamshid.unishop.domain.models.transfers.BasketProductModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SalesViewModel @Inject constructor(
    private val productApi: ProductApi
) : ViewModel() {

    private var _allProducts = MutableStateFlow<Response<List<Product>>>(Response.Default())
    val allProducts = _allProducts.asStateFlow()

    private val _saleProducts = MutableStateFlow<List<BasketProductModel>>(emptyList())
    val saleProducts:StateFlow<List<BasketProductModel>> get() = _saleProducts

    val counter: MutableStateFlow<Int> = MutableStateFlow(0);


    fun addProduct(basketProductModel: BasketProductModel) {
        viewModelScope.launch {
            val products = if (_saleProducts.value.isNotEmpty())
                _saleProducts.value as ArrayList<BasketProductModel>
            else
                ArrayList()

            for (basket in products) {
                if (basket.id == basketProductModel.id) return@launch
            }
            products.add(basketProductModel)
            counter.value = counter.value+1
            _saleProducts.value = products
        }
    }

    fun getAllProducts() {
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

    fun search(name: String) {
        viewModelScope.launch {
            try {
                _allProducts.emit(Response.Loading())
                val data = productApi.searchProduct(name)
                _allProducts.emit(Response.Success(data = data.map { it.toProduct() }))
            } catch (e: Exception) {
                _allProducts.emit(Response.Error(e.localizedMessage!!.toString()))
            }
        }
    }
}