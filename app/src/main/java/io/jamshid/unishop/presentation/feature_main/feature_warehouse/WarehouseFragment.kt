package io.jamshid.unishop.presentation.feature_main.feature_warehouse

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.R
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.common.Response
import io.jamshid.unishop.databinding.FragmentWarehouseBinding
import io.jamshid.unishop.domain.models.Category
import io.jamshid.unishop.presentation.MainActivity
import io.jamshid.unishop.presentation.feature_main.feature_warehouse.adapters.CategoryAdapter
import io.jamshid.unishop.presentation.feature_main.feature_warehouse.adapters.ProductAdapter
import io.jamshid.unishop.presentation.feature_main.feature_warehouse.utils.CategoryItemClick
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class WarehouseFragment :
    BaseFragment<FragmentWarehouseBinding>(FragmentWarehouseBinding::inflate) {


    private val viewModel: WarehouseViewModel by viewModels()
    private var categorySet = HashSet<Int>()


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun myCreateView(savedInstanceState: Bundle?) {
        (activity as MainActivity).showProgress(true)
        val categoryAdapter = CategoryAdapter(
            object : CategoryItemClick {
                override fun onClick(category: Category): Boolean {
                    return if (!categorySet.contains(category.id)) {
                        categorySet.add(category.id)
                        true
                    } else {
                        categorySet.remove(category.id)
                        false
                    }
                }
            },
            resources.getDrawable(R.drawable.selected_category_bg),
            resources.getDrawable(R.drawable.category_bg)
        )
        val productAdapter = ProductAdapter()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.allProducts.collectLatest { response ->

                when (response) {
                    is Response.Loading -> {
                        (activity as MainActivity).showProgress(true)
                    }
                    is Response.Success -> {
                        (activity as MainActivity).showProgress(false)
                        productAdapter.setData(response.data!!)
                    }
                    else -> {
                        (activity as MainActivity).showProgress(false)
                    }
                }

            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.allCategories.collectLatest {
                categoryAdapter.setData(it)
            }
        }

        binding.apply {
            rcvWareHouse.adapter = productAdapter
            rcvCategory.adapter = categoryAdapter
            imgAddProduct.setOnClickListener {
                findNavController().navigate(R.id.action_warehouseFragment_to_addProductFragment)
            }
        }


    }


}