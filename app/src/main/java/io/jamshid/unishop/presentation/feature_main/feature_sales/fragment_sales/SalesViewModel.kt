package io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_sales

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.jamshid.unishop.common.Response
import io.jamshid.unishop.data.remote.apis.ProductApi
import io.jamshid.unishop.domain.models.Product
import io.jamshid.unishop.domain.models.transfers.BasketProductModel
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

    private val _saleProducts = MutableStateFlow<List<BasketProductModel>>(emptyList())
    val saleProducts = _saleProducts


    fun addProduct(basketProductModel: BasketProductModel) {
        viewModelScope.launch {
            val products = if (_saleProducts.value.isNotEmpty())
                _saleProducts.value as ArrayList<BasketProductModel>
            else
                ArrayList()

            for (i in products.indices) {
                if (products[i].product.id == basketProductModel.product.id) {
                    val data =
                        products[i].copy(quantity = products[i].quantity + basketProductModel.quantity)
                    products[i] = data
                    _saleProducts.value=products
                    return@launch
                }
            }
            products.add(basketProductModel)
            _saleProducts.emit(products)
        }
    }


    init {
        getAllProducts()
    }

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