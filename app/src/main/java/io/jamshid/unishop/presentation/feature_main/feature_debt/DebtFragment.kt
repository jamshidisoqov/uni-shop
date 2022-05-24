package io.jamshid.unishop.presentation.feature_main.feature_debt

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.R
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.common.Response
import io.jamshid.unishop.data.models.dto.Output
import io.jamshid.unishop.data.models.dto.OutputSales
import io.jamshid.unishop.databinding.FragmentDebtBinding
import io.jamshid.unishop.presentation.feature_main.feature_debt.adapter.DebtAdapter
import io.jamshid.unishop.presentation.feature_main.feature_debt.util.DebtClickListener
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class DebtFragment : BaseFragment<FragmentDebtBinding>(FragmentDebtBinding::inflate) {


    private val viewModel: DebtViewModel by viewModels()


    override fun myCreateView(savedInstanceState: Bundle?) {

        viewModel.getAllDebt()

        val adapter = DebtAdapter(object : DebtClickListener {
            override fun onClick(outputSales: Output) {
                val bundle = Bundle()
                bundle.putInt("choose",0)
                bundle.putSerializable("output",outputSales);
                findNavController().navigate(R.id.action_debtFragment_to_debtDetailsFragment,bundle)
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