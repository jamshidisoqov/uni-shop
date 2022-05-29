package io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_sales

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.common.Response
import io.jamshid.unishop.databinding.FragmentSalesBinding
import io.jamshid.unishop.domain.models.transfers.BasketProductModel
import io.jamshid.unishop.presentation.MainActivity
import io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_baskets.util.Basket
import io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_sales.adapter.SalesListAdapter
import io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_sales.dialog.AddSalesDialog
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SalesFragment : BaseFragment<FragmentSalesBinding>(FragmentSalesBinding::inflate) {


    private val viewModel: SalesViewModel by viewModels()

    override fun myCreateView(savedInstanceState: Bundle?) {

        val adapter = SalesListAdapter()

        binding.rcvProductList.adapter = adapter

        adapter.setOnItemClickListener { product ->
            AddSalesDialog(viewModel, product).also {
                it.show(
                    requireActivity().supportFragmentManager,
                    it.tag
                )
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.allProducts.collect { response ->
                when (response) {
                    is Response.Loading -> {
                        (activity as MainActivity).showProgress(true)
                    }
                    is Response.Error -> {
                        Timber.d("Error %s", response.message)
                        (activity as MainActivity).showProgress(false)
                    }
                    is Response.Success -> {
                        response.data?.let { adapter.submitList(it) }
                        (activity as MainActivity).showProgress(false)
                    }
                    else -> Unit
                }
            }
        }

        navigate()


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.counter.collect { counter ->
                "$counter product".also {
                    binding.tvCounterBasket.text = it
                }
            }
        }

    }

    private fun navigate() {
        binding.apply {

            imgBack.setOnClickListener { findNavController().navigateUp() }

            basketContainer.setOnClickListener {
                if (viewModel.saleProducts.value.isNotEmpty()) {
                    val gsonPretty = GsonBuilder().setPrettyPrinting().create()
                    Basket.products = viewModel.saleProducts.value as ArrayList<BasketProductModel>
                    val gsonString = gsonPretty.toJson(viewModel.saleProducts.value)
                    findNavController().navigate(
                        SalesFragmentDirections.actionSalesFragmentToBasketFragment(
                            gsonString
                        )
                    )
                } else {
                    Snackbar.make(binding.basketContainer, "Basket empty!", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }

        }
    }
}