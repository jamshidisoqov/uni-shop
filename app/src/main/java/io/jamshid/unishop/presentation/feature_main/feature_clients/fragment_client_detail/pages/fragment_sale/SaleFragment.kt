package io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_client_detail.pages.fragment_sale

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.R
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.common.Response
import io.jamshid.unishop.common.extension_functions.toSummFormat
import io.jamshid.unishop.data.models.dto.OutputSales
import io.jamshid.unishop.databinding.FragmentSaleBinding
import io.jamshid.unishop.presentation.feature_main.dialog.ErrorDialog
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
                        val dialog = ErrorDialog("Error")
                        dialog.show(requireActivity().supportFragmentManager, "TAG")
                        showProgress(false)
                    }
                    is Response.Success -> {
                        response.data?.let {
                            listAdapter.setData(it)
                            calculate(it)
                        }
                        showProgress(false)
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun calculate(list: List<OutputSales>) {
        var sum = 0L
        var debt = 0L
        list.forEach {
            sum += it.amount.toLong()
            debt += it.debtAmount.toLong()
        }
        animateTotalPrice(0L, sum)
        animateTotalDebt(0L, debt)
    }

    @SuppressLint("SetTextI18n")
    private fun animateTotalPrice(start: Long, end: Long) {
        val animator = ValueAnimator.ofFloat(start.toFloat(), end.toFloat())
        animator.addUpdateListener {
            val newValue = (it.animatedValue as Float).toLong().toString().toSummFormat()
            binding.tvAllSum.text =getString(R.string.all) +  newValue
        }
        animator.duration = 500
        animator.start()
    }

    @SuppressLint("SetTextI18n")
    private fun animateTotalDebt(start: Long, end: Long) {
        val animator = ValueAnimator.ofFloat(start.toFloat(), end.toFloat())
        animator.addUpdateListener {
            val newValue = (it.animatedValue as Float).toLong().toString().toSummFormat()
            binding.tvDebtSumm.text = getString(R.string.all) + newValue
        }
        animator.duration = 500
        animator.start()
    }
}
