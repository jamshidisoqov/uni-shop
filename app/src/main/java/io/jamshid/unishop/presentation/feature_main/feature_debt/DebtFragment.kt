package io.jamshid.unishop.presentation.feature_main.feature_debt

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.common.Response
import io.jamshid.unishop.data.models.dto.OutputSales
import io.jamshid.unishop.databinding.FragmentDebtBinding
import io.jamshid.unishop.presentation.feature_main.feature_debt.adapter.DebtAdapter
import io.jamshid.unishop.presentation.feature_main.feature_debt.util.DebtClickListener
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class DebtFragment : BaseFragment<FragmentDebtBinding>(FragmentDebtBinding::inflate) {


    private val viewModel: DebtViewModel by viewModels()


    override fun myCreateView(savedInstanceState: Bundle?) {
        val adapter = DebtAdapter(object : DebtClickListener {
            override fun onClick(outputSales: OutputSales) {
                findNavController().navigate(DebtFragmentDirections.actionDebtFragmentToDebtDetailsFragment(outputSales))
            }
        }).also {
            binding.rcvListDebt.adapter = it
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.allDebt.collectLatest {
                when (it) {
                    is Response.Loading -> {
                        showProgress(true)
                    }
                    is Response.Success -> {
                        showProgress(false)
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