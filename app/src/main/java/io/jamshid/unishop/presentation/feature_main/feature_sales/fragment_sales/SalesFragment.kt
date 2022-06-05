package io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_sales

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.R
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.common.Response
import io.jamshid.unishop.databinding.FragmentSalesBinding
import io.jamshid.unishop.domain.models.transfers.BasketProductModel
import io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_baskets.util.Basket
import io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_sales.adapter.SalesListAdapter
import io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_sales.dialog.AddSalesDialog
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SalesFragment : BaseFragment<FragmentSalesBinding>(FragmentSalesBinding::inflate) {


    private val viewModel: SalesViewModel by viewModels()
    private var lastProductCount=0

    @SuppressLint("SetTextI18n")
    override fun myCreateView(savedInstanceState: Bundle?) {

        val adapter = SalesListAdapter()

        viewModel.getAllProducts()

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
                        binding.pbSales.visibility = View.VISIBLE
                    }
                    is Response.Error -> {
                        binding.pbSales.visibility = View.INVISIBLE
                    }
                    is Response.Success -> {
                        binding.pbSales.visibility = View.INVISIBLE
                        adapter.submitList(response.data!!)
                    }
                    else -> Unit
                }
            }
        }

        navigate()


        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.counter.collectLatest { counter ->
                if (counter>0)
                binding.tvCounterBasket.text = "$counter  " + getString(R.string.products)
            }
        }

    }

    private fun navigate() {
        binding.apply {

            imgBack.setOnClickListener { findNavController().navigateUp() }

            basketContainer.setOnClickListener {

                if (viewModel.saleProducts.value.isNotEmpty()) {

                    Basket.products = viewModel.saleProducts.value as ArrayList<BasketProductModel>
                    findNavController().navigate(R.id.action_salesFragment_to_basketFragment)
                } else {
                    Snackbar.make(
                        binding.basketContainer,
                        getString(R.string.basket_empty),
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                }
            }

            edSearchProduct.addTextChangedListener {
                viewModel.search(it!!.toString())
            }

        }
    }
}