package io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_baskets

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.databinding.FragmentBasketBinding

class BasketFragment : BaseFragment<FragmentBasketBinding>(FragmentBasketBinding::inflate) {

    private val viewModel: BasketViewModel by viewModels()

    private val args: BasketFragmentArgs by navArgs()

    override fun myCreateView(savedInstanceState: Bundle?) {

    }
}