package io.jamshid.unishop.presentation.feature_main.feature_debt.fragment_details

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.common.extension_functions.getDateFormat
import io.jamshid.unishop.databinding.FragmentDebtDetailsBinding
import java.util.*

@AndroidEntryPoint
class DebtDetailsFragment :
    BaseFragment<FragmentDebtDetailsBinding>(FragmentDebtDetailsBinding::inflate) {

    private val viewModel: DebtDetailsViewModel by viewModels()
    private val args: DebtDetailsFragmentArgs by navArgs()

    override fun myCreateView(savedInstanceState: Bundle?) {

        showProgress(true)
        viewModel.allProducts(args.output.id)
        viewModel.allPayments(args.output.id)

        binding.apply {
            args.output.also {
                this.tvClientName.text = it.client.fullName
                this.debtExpireDate.text = Date(it.expiredDate.time).toString().getDateFormat()
                this.lastPaymentDate.text = Date(it.updatedDate.time).toString().getDateFormat()
                this.tvBuyDate.text = Date(it.createdDate.time).toString().getDateFormat()
                this.tvBuySumm.text = it.amount.toString()
                this.tvDebtSumm.text = it.debtAmount.toString()
            }

        }
    }
}