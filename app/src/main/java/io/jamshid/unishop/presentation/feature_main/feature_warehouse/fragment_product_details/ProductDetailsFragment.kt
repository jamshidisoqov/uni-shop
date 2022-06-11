package io.jamshid.unishop.presentation.feature_main.feature_warehouse.fragment_product_details

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.common.Response
import io.jamshid.unishop.common.extension_functions.getOnlyDigits
import io.jamshid.unishop.common.extension_functions.toSummFormat
import io.jamshid.unishop.databinding.DilogChangeProductBinding
import io.jamshid.unishop.databinding.FragmentProductDetailsBinding
import io.jamshid.unishop.domain.models.Product
import io.jamshid.unishop.utils.MaskWatcherNothing
import io.jamshid.unishop.utils.MaskWatcherPayment
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ProductDetailsFragment :
    BaseFragment<FragmentProductDetailsBinding>(FragmentProductDetailsBinding::inflate) {


    private val viewModel: ProductDetailsViewModel by viewModels()
    private lateinit var args: Product

    override fun myCreateView(savedInstanceState: Bundle?) {


        if (arguments != null) {
            args = requireArguments().getSerializable("product") as Product

            viewModel.setProduct(args)


            binding.fabAddProduct.setOnClickListener {
                showDialog(args)
            }


        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.product.collectLatest {

                when (it) {
                    is Response.Loading -> {

                    }
                    is Response.Success -> {
                        showProgress(false)

                        binding.apply {
                            val args = it.data!!
                            tvProductName.text = args.name
                            tvProductBrand.text = args.brand
                            edProductQuantity.setText(args.quantity.toString() + " штук")
                            edProductPrice.setText(args.price.toLong().toString().toSummFormat())
                            edProductMinimumPrices.setText(
                                args.minimumPrice.toLong().toString().toSummFormat()
                            )
                            edProductMaximumPrices.setText(
                                args.maximumPrice.toLong().toString().toSummFormat()
                            )
                        }

                        args = it.data!!
                    }
                    else -> {
                        showProgress(false)
                    }
                }
            }
        }


    }

    private fun showDialog(product: Product) {


        val dialog = BottomSheetDialog(requireContext())

        val detailsBinding = DilogChangeProductBinding.inflate(layoutInflater, null, false)

        detailsBinding.apply {


            edProductQuantity.addTextChangedListener(MaskWatcherNothing(edProductQuantity))
            edProductPrice.addTextChangedListener(MaskWatcherPayment(edProductPrice))
            edProductMaximumPrices.addTextChangedListener(MaskWatcherPayment(edProductMaximumPrices))
            edProductMinimumPrices.addTextChangedListener(MaskWatcherPayment(edProductMinimumPrices))


            tvProductName.text = product.name
            edProductQuantity.setText(product.quantity.toString())
            edProductPrice.setText(product.price.toString().toSummFormat())
            edProductMinimumPrices.setText(product.minimumPrice.toString().toSummFormat())
            edProductMaximumPrices.setText(product.maximumPrice.toString().toSummFormat())



            btnChange.setOnClickListener {

                val p = product.copy(
                    quantity = edProductQuantity.text.toString().getOnlyDigits().toInt()+product.quantity,
                    price = edProductPrice.text.toString().getOnlyDigits().toDouble(),
                    minimumPrice = edProductMinimumPrices.text.toString().getOnlyDigits()
                        .toDouble(),
                    maximumPrice = edProductMaximumPrices.text.toString().getOnlyDigits().toDouble()
                )

                viewModel.updateProduct(p.toProductDto())
                dialog.dismiss()

            }
        }

        dialog.setContentView(detailsBinding.root)
        dialog.show()

    }


}