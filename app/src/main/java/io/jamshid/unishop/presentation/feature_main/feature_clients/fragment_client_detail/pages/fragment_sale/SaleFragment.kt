package io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_client_detail.pages.fragment_sale

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.common.Response
import io.jamshid.unishop.databinding.FragmentSaleBinding
import io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_client_detail.pages.fragment_sale.adapter.SaleListAdapter
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@AndroidEntryPoint
class SaleFragment : BaseFragment<FragmentSaleBinding>(FragmentSaleBinding::inflate) {

    private val viewModel: SaleViewModel by viewModels()

    override fun myCreateView(savedInstanceState: Bundle?) {

        val listAdapter = SaleListAdapter().also { binding.saleListView.adapter = it }

        if (arguments != null)
            viewModel.getAllSales(arguments?.getLong("clientId")!!)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.allSales.collectLatest { response ->
                when (response) {
                    is Response.Loading -> {
                        //binding.loading.show()
                    }
                    is Response.Error -> {
                        Timber.d("Error %s", response.message)
                        //binding.loading.hide()
                    }
                    is Response.Success -> {
                        response.data?.let {
                            listAdapter.setData(it)
                        }
                        // binding.loading.hide()
                    }
                    else -> Unit
                }
            }
        }

    }
}
