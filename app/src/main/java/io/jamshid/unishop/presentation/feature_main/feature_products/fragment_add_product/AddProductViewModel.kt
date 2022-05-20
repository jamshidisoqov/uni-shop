package io.jamshid.unishop.presentation.feature_main.feature_products.fragment_add_product

import android.content.ContentValues
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.jamshid.unishop.data.remote.apis.CategoryApi
import io.jamshid.unishop.data.remote.apis.ProductApi
import io.jamshid.unishop.domain.models.Category
import io.jamshid.unishop.domain.models.Product
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

// Created by Usmon Abdurakhmanv on 5/13/2022.

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val productApi: ProductApi,
    private val categoryApi: CategoryApi
) : ViewModel() {

    private val _allCategories = MutableStateFlow<List<Category>>(emptyList())
    val allCategories = _allCategories.asStateFlow()

    private val _productChannel = Channel<Product>()
    val product = _productChannel.receiveAsFlow()

    init {
        fetchAllCategories()
    }

    private fun fetchAllCategories() {
        viewModelScope.launch {
            val categories = categoryApi
                .getAllCategory()
                .map { it.toCategory() }
            _allCategories.emit(categories)
        }
    }


    fun addProduct(product: Product) {
        viewModelScope.launch {
            val productDto = product.toProductDto()
            val result = productApi.addProduct(productDto).body
            _productChannel.send(result!!.toProduct())
        }
    }

    fun addCategory(category: Category) {
        viewModelScope.launch {
            val result = categoryApi.addCategory(category.toCategoryDto())
            Timber.tag(ContentValues.TAG).d("addCategory: %s", result.toString())
        }
    }
}