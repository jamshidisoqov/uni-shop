package io.jamshid.unishop.presentation.feature_main.feature_products.fragment_add_product

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.R
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.common.extension_functions.getOnlyDigits
import io.jamshid.unishop.databinding.FragmentAddProductBinding
import io.jamshid.unishop.domain.models.Category
import io.jamshid.unishop.domain.models.Product
import io.jamshid.unishop.presentation.MainActivity
import io.jamshid.unishop.presentation.feature_main.dialog.ErrorDialog
import io.jamshid.unishop.presentation.feature_main.dialog.NoConnectionDialog
import io.jamshid.unishop.presentation.feature_main.feature_products.fragment_add_product.adapter.CategorySpinnerAdapter
import io.jamshid.unishop.presentation.feature_main.feature_products.fragment_add_product.dialog.NewCategoryDialog
import io.jamshid.unishop.utils.MaskWatcherNothing
import io.jamshid.unishop.utils.MaskWatcherPayment
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class AddProductFragment :
    BaseFragment<FragmentAddProductBinding>(FragmentAddProductBinding::inflate) {

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

            edProductQuantity.addTextChangedListener(MaskWatcherNothing(edProductQuantity))
            edProductPrice.addTextChangedListener(MaskWatcherPayment(edProductPrice))
            edProductMinimumPrices.addTextChangedListener(MaskWatcherPayment(edProductMinimumPrices))
            edProductMaximumPrices.addTextChangedListener(MaskWatcherPayment(edProductMaximumPrices))

            btnAddNewProduct.setOnClickListener {
                val name = edProductName.text.toString()
                val brand = edProductBrand.text.toString()
                val price = edProductPrice.text.toString().getOnlyDigits()
                val quantity = edProductQuantity.text.toString().getOnlyDigits().toInt()
                val minPrice = edProductMinimumPrices.text.toString().getOnlyDigits()
                val maxPrice = edProductMaximumPrices.text.toString().getOnlyDigits()
                val categoryId = (actvCategory.selectedItem as Category).id

                val fieldsNotValid = name.isNotBlank() &&
                        brand.isNotBlank() &&
                        price.isNotBlank() &&
                        minPrice.isNotBlank() &&
                        maxPrice.isNotBlank() &&
                        categoryId > 0

                if (fieldsNotValid) {
                    if (price <= minPrice && minPrice < maxPrice) {
                        if ((activity as MainActivity).isConnected())
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
                        )else {
                            val dialog = NoConnectionDialog("Error")
                            dialog.show(requireActivity().supportFragmentManager,"TAG")
                        }
                        binding.apply {
                            edProductBrand.setText("")
                            edProductName.setText("")
                            edProductQuantity.setText("")
                            edProductPrice.setText("")
                            edProductMinimumPrices.setText("")
                            edProductMaximumPrices.setText("")
                        }
                    } else {
                        edProductPrice.error = getString(R.string.product_sum_error)
                        edProductMaximumPrices.error = getString(R.string.product_sum_error)
                        edProductMinimumPrices.error = getString(R.string.product_sum_error)
                    }
                    viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                        viewModel.product.collectLatest {

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

            btnProductList.setOnClickListener {

            }

            imgBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

}