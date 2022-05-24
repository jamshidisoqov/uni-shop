package io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_client_detail.pages.fragment_sale

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.R
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.common.Response
import io.jamshid.unishop.data.models.dto.OutputSales
import io.jamshid.unishop.databinding.FragmentSaleBinding
import io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_client_detail.pages.fragment_sale.adapter.SaleListAdapter
import io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_client_detail.pages.fragment_sale.utils.SalesClickListener
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SaleFragment : BaseFragment<FragmentSaleBinding>(FragmentSaleBinding::inflate) {

    private val viewModel: SaleViewModel by viewModels()

    override fun myCreateView(savedInstanceState: Bundle?) {

        val listAdapter = SaleListAdapter(object : SalesClickListener {
            override fun onClick(outputSales: OutputSales) {
                val bundle = Bundle()
                bundle.putSerializable("output", outputSales.toOutput())
                bundle.putInt("choose", 1)
                findNavController().navigate(R.id.debtDetailsFragment, bundle)
            }
        }).also { binding.saleListView.adapter = it }

        if (arguments != null)
            viewModel.getAllSales(arguments?.getLong("clientId")!!)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.allSales.collectLatest { response ->
                when (response) {
                    is Response.Loading -> {
                        showProgress(true)
                    }
                    is Response.Error -> {
                        showProgress(false)
                    }
                    is Response.Success -> {
                        response.data?.let {
                            listAdapter.setData(it)
                        }
                        showProgress(false)
                    }
                    else -> Unit
                }
            }
        }

    }
}
