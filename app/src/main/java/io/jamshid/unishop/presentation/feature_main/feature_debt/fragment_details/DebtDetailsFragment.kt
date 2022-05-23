package io.jamshid.unishop.presentation.feature_main.feature_debt.fragment_details

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.common.Response
import io.jamshid.unishop.common.extension_functions.getDateFormat
import io.jamshid.unishop.data.models.dto.Output
import io.jamshid.unishop.databinding.FragmentDebtDetailsBinding
import io.jamshid.unishop.presentation.feature_main.feature_warehouse.adapters.ProductAdapter
import kotlinx.coroutines.flow.collectLatest
import java.util.*

@AndroidEntryPoint
class DebtDetailsFragment :
    BaseFragment<FragmentDebtDetailsBinding>(FragmentDebtDetailsBinding::inflate) {

    private val viewModel: DebtDetailsViewModel by viewModels()
    private val args: DebtDetailsFragmentArgs by navArgs()
    private lateinit var outputSales: Output


    override fun myCreateView(savedInstanceState: Bundle?) {


        val adapter = ProductAdapter().also {
            binding.productListDebt.adapter = it
        }

        showProgress(true)
        if (arguments != null) {
            outputSales = arguments?.getSerializable("output") as Output
            viewModel.allProducts(outputSales.id)
            viewModel.allPayments(outputSales.id)

            binding.apply {
                outputSales.also {
                    this.tvClientName.text = it.client
                    this.debtExpireDate.text = Date(it.expiredDate.time).toString().getDateFormat()
                    this.lastPaymentDate.text =
                        Date(it.updatedDate!!.time).toString().getDateFormat()
                    this.tvBuyDate.text = Date(it.createdDate.time).toString().getDateFormat()
                    this.tvBuySumm.text = it.amount.toString()
                    this.tvDebtSumm.text = it.debtAmount.toString()
                }

            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.allProductsByOutput.collectLatest {
                when (it) {
                    is Response.Loading -> {
                        showProgress(true)
                    }
                    is Response.Success -> {
                        showProgress(false)
                        Log.d(TAG, "myCreateView: ${it.data!!}")
                        adapter.setData(it.data!!)
                    }
                    else -> {
                        showProgress(false)
                    }
                }
            }
        }
    }
}