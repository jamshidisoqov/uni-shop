package io.jamshid.unishop.presentation.feature_main.feature_warehouse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.jamshid.unishop.common.Response
import io.jamshid.unishop.data.remote.apis.CategoryApi
import io.jamshid.unishop.data.remote.apis.ProductApi
import io.jamshid.unishop.domain.models.Category
import io.jamshid.unishop.domain.models.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WarehouseViewModel @Inject constructor(
    private val productApi: ProductApi,
    private val categoryApi: CategoryApi
) : ViewModel() {

    private var _allProducts = MutableStateFlow<Response<List<Product>>>(Response.Default())
    val allProducts = _allProducts.asStateFlow()


    private val _allCategories = MutableStateFlow<Response<List<Category>>>(Response.Default())
    val allCategories = _allCategories.asStateFlow()


    init {
        getAllProducts()
        fetchAllCategories()
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

    private fun fetchAllCategories() {
        viewModelScope.launch {
            try {
                _allCategories.emit(Response.Loading())
                val categories = categoryApi
                    .getAllCategory()
                    .map {
                        it.toCategory()
                    }
                _allCategories.emit(Response.Success(categories))
            } catch (e: Exception) {
                _allCategories.emit(Response.Error(e.localizedMessage!!.toString()))
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