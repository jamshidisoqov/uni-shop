package io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_baskets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.jamshid.unishop.domain.models.transfers.BasketProductModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BasketViewModel : ViewModel() {

    private var _saleProducts = MutableStateFlow<List<BasketProductModel>>(emptyList())
    val saleProducts = _saleProducts.asStateFlow()


    fun setSellProducts(list: List<BasketProductModel>) {
        _saleProducts.value = list
    }

    fun addProduct(basket: BasketProductModel) {
        viewModelScope.launch {
            val products = saleProducts.value as ArrayList
            val ind = products.indexOf(basket)
            if (products[ind].quantity < basket.quantity)
                products[ind] = products[ind].copy(
                    quantity = products[ind].quantity + 1
                )
            _saleProducts.emit(products)
        }
    }

    fun removeProduct(basket: BasketProductModel) {
        viewModelScope.launch {
            val products = saleProducts.value as ArrayList
            val ind = products.indexOf(basket)
            if (products[ind].quantity > 0)
                products[ind] = products[ind].copy(
                    quantity = products[ind].quantity - 1
                )
            _saleProducts.emit(products)
        }
    }

    fun deleteProduct(product: BasketProductModel) {
        //TODO: Delete bo'lishi karak
    }

}