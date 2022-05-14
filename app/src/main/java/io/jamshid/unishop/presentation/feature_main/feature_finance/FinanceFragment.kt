package io.jamshid.unishop.presentation.feature_main.feature_finance

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.R
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.databinding.FragmentFinanceBinding


/*TZ
* circle progress indicator
* barChart last 7 month
* navigate
* balance = sof foyda
* income = balance
* */

@AndroidEntryPoint
class FinanceFragment : BaseFragment<FragmentFinanceBinding>(FragmentFinanceBinding::inflate) {

    private val viewModel: FinanceViewModel by viewModels()

    override fun myCreateView(savedInstanceState: Bundle?) {
        navigate()
    }

    private fun navigate() {

        binding.apply {

            containerBalance.setOnClickListener {
                findNavController().navigate(R.id.action_financeFragment_to_incomeFragment)
            }
            containerExpanses.setOnClickListener {
                findNavController().navigate(R.id.action_financeFragment_to_expansesFragment)
            }
            containerIncome.setOnClickListener {
                findNavController().navigate(R.id.action_financeFragment_to_incomeFragment)
            }
        }
    }


}