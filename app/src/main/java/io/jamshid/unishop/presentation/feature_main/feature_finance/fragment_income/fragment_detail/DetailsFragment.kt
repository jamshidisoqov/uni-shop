package io.jamshid.unishop.presentation.feature_main.feature_finance.fragment_income.fragment_detail

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.databinding.FragmentDetailsBinding

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {


    private val viewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()


    override fun myCreateView(savedInstanceState: Bundle?) {
        viewModel.getProductsByOutPut(args.outPutId)
    }


}