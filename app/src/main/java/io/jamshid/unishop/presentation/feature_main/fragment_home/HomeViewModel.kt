package io.jamshid.unishop.presentation.feature_main.fragment_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.jamshid.unishop.data.remote.apis.CategoryApi
import io.jamshid.unishop.domain.models.Category
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private var categoryApi: CategoryApi
) : ViewModel() {


    fun addCategory(category: Category) {
        viewModelScope.launch {
            categoryApi.addCategory(category.toCategoryDto())
        }
    }

}