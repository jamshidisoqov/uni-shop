package io.jamshid.unishop.presentation.feature_main.feature_debt

import android.os.Bundle
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.databinding.FragmentDebtBinding

@AndroidEntryPoint
class DebtFragment : BaseFragment<FragmentDebtBinding>(FragmentDebtBinding::inflate) {


    private val viewModel: DebtViewModel by viewModels()


    override fun myCreateView(savedInstanceState: Bundle?) {

    }


}