package io.jamshid.unishop.presentation.feature_main.feature_finance.fragment_income

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.common.Response
import io.jamshid.unishop.data.models.dto.OutputSales
import io.jamshid.unishop.databinding.FragmentIncomeBinding
import io.jamshid.unishop.presentation.MainActivity
import io.jamshid.unishop.presentation.feature_main.feature_finance.fragment_income.adapter.IncomeAdapter
import io.jamshid.unishop.utils.OnItemClickListener
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class IncomeFragment : BaseFragment<FragmentIncomeBinding>(FragmentIncomeBinding::inflate) {

    private val viewModel: IncomeViewModel by viewModels()

    override fun myCreateView(savedInstanceState: Bundle?) {


        val adapter = IncomeAdapter(object : OnItemClickListener<OutputSales> {
            override fun onClick(data: OutputSales) {
                findNavController().navigate(
                    IncomeFragmentDirections.actionIncomeFragmentToDetailsFragment(
                        data.id
                    )
                )
            }
        })

        viewModel.getAllSales()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.allSales.collectLatest {
                when (it) {
                    is Response.Loading -> {
                        (activity as MainActivity).showProgress(true)
                    }
                    is Response.Success -> {
                        (activity as MainActivity).showProgress(false)
                        adapter.setData(it.data!!)
                    }
                    else -> {
                        (activity as MainActivity).showProgress(false)
                    }
                }
            }
        }

        binding.apply {
            rcvSalesAll.adapter = adapter
        }
        navigate()
    }

    private fun navigate() {

        binding.apply {
            imgBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }
}