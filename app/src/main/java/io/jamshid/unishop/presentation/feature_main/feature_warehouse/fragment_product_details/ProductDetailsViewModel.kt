package io.jamshid.unishop.presentation.feature_main.feature_warehouse.fragment_product_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.jamshid.unishop.common.Response
import io.jamshid.unishop.data.models.dto.ProductDto
import io.jamshid.unishop.data.remote.apis.ProductApi
import io.jamshid.unishop.domain.models.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val productApi: ProductApi
) : ViewModel() {

    var product: MutableStateFlow<Response<Product>> = MutableStateFlow(Response.Loading())


    fun updateProduct(productDto: ProductDto) {
        viewModelScope.launch {
            try {
                product.emit(Response.Loading())
                setProduct(productDto.toProduct())
                val date = productApi.updateProduct(productDto.id!!, productDto)
                product.emit(Response.Success(date.body!!.toProduct()))
            } catch (e: Exception) {
                //Timber.tag(TAG).d("updateProduct: %s", e.localizedMessage!!)
                //product.emit(Response.Error(e.localizedMessage!!))
            }
        }
    }

    fun setProduct(p: Product) {
        viewModelScope.launch {
            product.emit(Response.Success(p))
        }
    }

}