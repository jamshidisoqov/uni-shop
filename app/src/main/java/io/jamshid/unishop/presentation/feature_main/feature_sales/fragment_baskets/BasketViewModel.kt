package io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_baskets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.jamshid.unishop.data.remote.apis.ProductApi
import io.jamshid.unishop.domain.models.transfers.BasketProductModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val productApi: ProductApi
) : ViewModel() {

    private var _saleProducts = MutableStateFlow<List<BasketProductModel>>(emptyList())
    val saleProducts = _saleProducts.asStateFlow()

    private var quantity = 0


    fun setSellProducts(list: List<BasketProductModel>) {
        _saleProducts.value = list
    }

    fun addProduct(basket: BasketProductModel) {

        viewModelScope.launch {

            getQuantity(basket.id)

            val products = saleProducts.value as ArrayList

            var ind = 0
            for (i in saleProducts.value.indices) {
                if (saleProducts.value[i].id == basket.id) {
                    ind = i
                    break
                }
            }
            if (products[ind].quantity < quantity)
                products[ind] = products[ind].copy(
                    quantity = products[ind].quantity + 1
                )
            _saleProducts.emit(products)
        }
    }

    private fun getQuantity(id: Long) {
        viewModelScope.launch {
            quantity = productApi.getProduct(id).quantity!!
        }
    }

    fun removeProduct(basket: BasketProductModel) {
        viewModelScope.launch {
            val products = saleProducts.value as ArrayList
            var ind = 0
            for (i in saleProducts.value.indices) {
                if (saleProducts.value[i].id == basket.id) {
                    ind = i
                    break
                }
            }
            products[ind] = products[ind].copy(
                quantity = products[ind].quantity - 1
            )
            if (products[ind].quantity == 0) {
                products.removeAt(ind)
            }
            _saleProducts.emit(products)
        }
    }

    fun deleteProduct(product: BasketProductModel) {
        //TODO: Delete
    }

}