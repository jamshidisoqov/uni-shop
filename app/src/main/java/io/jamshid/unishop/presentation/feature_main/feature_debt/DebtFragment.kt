package io.jamshid.unishop.presentation.feature_main.feature_debt

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.R
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.common.Response
import io.jamshid.unishop.data.models.dto.Client
import io.jamshid.unishop.data.models.dto.Output
import io.jamshid.unishop.databinding.FragmentDebtBinding
import io.jamshid.unishop.presentation.feature_main.feature_debt.adapter.DebtAdapter
import io.jamshid.unishop.presentation.feature_main.feature_debt.util.Constants
import io.jamshid.unishop.presentation.feature_main.feature_debt.util.DebtClickListener
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class DebtFragment : BaseFragment<FragmentDebtBinding>(FragmentDebtBinding::inflate) {


    private val viewModel: DebtViewModel by viewModels()


    override fun myCreateView(savedInstanceState: Bundle?) {

        if (Constants.choose == 1)
            viewModel.getAllDebt()

        val adapter = DebtAdapter(object : DebtClickListener {
            override fun onClick(outputSales: Output) {
                val bundle = Bundle()
                bundle.putInt("choose", 0)
                bundle.putSerializable("output", outputSales);
                findNavController().navigate(
                    R.id.action_debtFragment_to_debtDetailsFragment,
                    bundle
                )
            }
        }).also {
            binding.rcvListDebt.adapter = it
        }

        if (Constants.choose == 2) {
            val client = arguments?.getSerializable("client") as Client
            viewModel.searchDebt(client.fullName!!)
            binding.edSearchDebt.setText(client.fullName)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.allDebt.collectLatest {
                when (it) {
                    is Response.Loading -> {
                        binding.pbDebt.visibility = View.VISIBLE
                    }
                    is Response.Success -> {
                        binding.pbDebt.visibility = View.INVISIBLE
                        adapter.setData(it.data!!)
                    }
                    else -> {
                        binding.pbDebt.visibility = View.INVISIBLE
                    }
                }
            }
        }

        binding.apply {
            imgBack.setOnClickListener {
                findNavController().navigateUp()
            }
            edSearchDebt.addTextChangedListener {
                viewModel.searchDebt(it!!.toString())
            }
        }

    }


}