package io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_sales

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.gson.GsonBuilder
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.common.Response
import io.jamshid.unishop.databinding.FragmentSalesBinding
import io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_sales.adapter.SalesListAdapter
import io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_sales.dialog.AddSalesDialog
import kotlinx.coroutines.flow.collectLatest
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
            viewModel.allProducts.collectLatest { response ->
                when (response) {
                    is Response.Loading -> {
                        binding.loading.show()
                    }
                    is Response.Error -> {
                        Timber.d("Error %s", response.message)
                        binding.loading.hide()
                    }
                    is Response.Success -> {
                        response.data?.let { adapter.submitList(it) }
                        binding.loading.hide()
                    }
                    else -> Unit
                }
            }


        }

        navigate()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.saleProducts.collectLatest { products ->
                binding.apply {
                    "${products.size} product".also {
                        tvCounterBasket.text = it
                    }
                    var summa = 0.0
                    for (product in products) {
                        summa += product.cost * product.quantity
                    }
                    "$summa UZS".also {
                        tvProductBasketSumm.text = it
                    }
                }
            }
        }

        binding.imgBack.setOnClickListener { findNavController().navigateUp() }
    }

    private fun navigate() {
        binding.apply {
            imgBack.setOnClickListener { findNavController().navigateUp() }

            basketContainer.setOnClickListener {
                val gsonPretty = GsonBuilder().setPrettyPrinting().create()
                val gsonString = gsonPretty.toJson(viewModel.saleProducts.value)
                findNavController().navigate(
                    SalesFragmentDirections.actionSalesFragmentToBasketFragment(
                        gsonString
                    )
                )
            }
        }
    }
}