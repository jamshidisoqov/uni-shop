package io.jamshid.unishop.presentation.feature_main.feature_finance.fragment_income

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.databinding.FragmentIncomeBinding

class IncomeFragment : BaseFragment<FragmentIncomeBinding>(FragmentIncomeBinding::inflate) {

    private val viewModel: IncomeViewModel by viewModels()

    override fun myCreateView(savedInstanceState: Bundle?) {

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