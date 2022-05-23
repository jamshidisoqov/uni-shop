package io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_baskets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.jamshid.unishop.domain.models.transfers.BasketProductModel
import io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_baskets.util.Basket
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

            var ind=0
            for (i in saleProducts.value.indices){
                if(saleProducts.value[i].id==basket.id){
                    ind=i
                    break
                }
            }
            if (products[ind].quantity < 10)
                products[ind] = products[ind].copy(
                    quantity = products[ind].quantity + 1
                )
            _saleProducts.emit(products)
        }
    }

    fun removeProduct(basket: BasketProductModel) {
        viewModelScope.launch {
            val products = saleProducts.value as ArrayList
            var ind=0
            for (i in saleProducts.value.indices){
                if(saleProducts.value[i].id==basket.id){
                    ind=i
                    break
                }
            }
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