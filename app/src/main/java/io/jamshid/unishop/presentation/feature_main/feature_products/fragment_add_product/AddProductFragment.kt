package io.jamshid.unishop.presentation.feature_main.feature_products.fragment_add_product

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.common.extension_functions.getOnlyDigits
import io.jamshid.unishop.databinding.FragmentAddProductBinding
import io.jamshid.unishop.domain.models.Category
import io.jamshid.unishop.domain.models.Product
import io.jamshid.unishop.presentation.feature_main.feature_products.fragment_add_product.adapter.CategorySpinnerAdapter
import io.jamshid.unishop.presentation.feature_main.feature_products.fragment_add_product.dialog.NewCategoryDialog
import kotlinx.coroutines.flow.collectLatest

// Created by Usmon Abdurakhmanv on 5/13/2022.

@AndroidEntryPoint
class AddProductFragment : BaseFragment<FragmentAddProductBinding>(FragmentAddProductBinding::inflate) {

    private val viewModel: AddProductViewModel by viewModels()

    override fun myCreateView(savedInstanceState: Bundle?) {

        val adapter = CategorySpinnerAdapter()
        binding.actvCategory.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.allCategories.collectLatest { categories ->
                adapter.submitData(categories)
            }
        }

        binding.apply {
            btnAddNewProduct.setOnClickListener {
                val name = edProductName.text.toString()
                val brand = edProductBrand.text.toString()
                val price = edProductPrice.text.toString()
                val quantity = edProductQuantity.text.toString().getOnlyDigits().toInt()
                val minPrice = edProductMinimumPrices.text.toString()
                val maxPrice = edProductMaximumPrices.text.toString()
                val categoryId = (actvCategory.selectedItem as Category).id

                val fieldsNotValid = name.isNotBlank() &&
                        brand.isNotBlank() &&
                        price.isNotBlank() &&
                        minPrice.isNotBlank() &&
                        maxPrice.isNotBlank() &&
                        categoryId > 0

                if (fieldsNotValid) {
                    if (price <= minPrice && minPrice < maxPrice)
                        viewModel.addProduct(
                            Product(
                                id = 0,
                                name = name,
                                description = "birzat",
                                brand = brand,
                                quantity = quantity,
                                price = price.toDouble(),
                                minimumPrice = minPrice.toDouble(),
                                maximumPrice = maxPrice.toDouble(),
                                categoryId = categoryId
                            )
                        )
                    else {
                        edProductPrice.error = "To'ldirilishi shart"
                        edProductMaximumPrices.error = "To'ldirilishi shart"
                        edProductMinimumPrices.error = "To'ldirilishi shart"
                    }
                    viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                        viewModel.product.collectLatest {
                            Toast.makeText(requireContext(), "$it", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Snackbar.make(
                        this.btnAddNewProduct,
                        "Property not filled",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }

            addCategory.setOnClickListener {
                val dialog = NewCategoryDialog(viewModel)
                dialog.show(requireActivity().supportFragmentManager, dialog.tag)
            }
            imgBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }
}