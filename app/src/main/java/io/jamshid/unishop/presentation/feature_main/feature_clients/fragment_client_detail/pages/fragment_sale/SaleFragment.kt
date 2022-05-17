package io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_client_detail.pages.fragment_sale

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.databinding.FragmentSaleBinding
import io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_client_detail.pages.fragment_sale.adapter.SaleListAdapter
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SaleFragment : BaseFragment<FragmentSaleBinding>(FragmentSaleBinding::inflate) {

    private val viewModel: SaleViewModel by viewModels()

    override fun myCreateView(savedInstanceState: Bundle?) {

        val listAdapter = SaleListAdapter().also { binding.saleListView.adapter = it }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.salesState.collectLatest { sales ->
                listAdapter.submitList(sales)
            }
        }

        listAdapter.setOnItemClickListener { sale ->
            // TODO: sale will come but how to use
        }
    }

    companion object {
        fun newInstance(arguments: Bundle?) = SaleFragment().apply {
            this.arguments = arguments
        }
    }
}
