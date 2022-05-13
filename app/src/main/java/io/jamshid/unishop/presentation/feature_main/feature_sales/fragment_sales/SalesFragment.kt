package io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_sales

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.databinding.FragmentSalesBinding
import io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_sales.adapter.SalesListAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SalesFragment : BaseFragment<FragmentSalesBinding>(FragmentSalesBinding::inflate) {


    private val viewModel: SalesViewModel by viewModels()

    override fun myCreateView(savedInstanceState: Bundle?) {

        val adapter = SalesListAdapter()
        binding.rcvProductList.adapter = adapter

        adapter.setOnItemClickListener { product, position ->

        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.allProducts.collectLatest {
                it.data?.let { list ->
                    adapter.submitList(list)
                }
            }
        }

        binding.imgBack.setOnClickListener { findNavController().navigateUp() }
    }
}