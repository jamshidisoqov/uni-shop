package io.jamshid.unishop.presentation.feature_main.feature_finance

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
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

    }


}