package io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_baskets

import androidx.lifecycle.ViewModel
import io.jamshid.unishop.domain.models.transfers.BasketProductModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class BasketViewModel : ViewModel() {

    private var _saleProducts = MutableStateFlow<List<BasketProductModel>>(emptyList())
    val saleProducts = _saleProducts.asStateFlow()


    fun setSellProducts(list: List<BasketProductModel>) {
        _saleProducts.value = list
    }

    fun addProduct(basket: BasketProductModel) {
        val products = saleProducts.value as ArrayList
        val ind = products.indexOf(basket)
        if (products[ind].quantity < basket.quantity)
            products[ind] = products[ind].copy(
                quantity = products[ind].quantity + 1
            )
        _saleProducts.value = products
    }

    fun removeProduct(basket: BasketProductModel) {
        val products = saleProducts.value as ArrayList
        val ind = products.indexOf(basket)
        if (products[ind].quantity < basket.quantity)
            products[ind] = products[ind].copy(
                quantity = products[ind].quantity - 1
            )
        _saleProducts.value = products
    }

    fun deleteProduct(product: BasketProductModel) {
        //TODO: Delete bo'lishi karak
    }

}