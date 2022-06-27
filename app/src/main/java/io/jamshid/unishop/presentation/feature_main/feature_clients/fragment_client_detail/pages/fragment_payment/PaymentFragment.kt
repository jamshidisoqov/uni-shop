package io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_client_detail.pages.fragment_payment

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.common.Response
import io.jamshid.unishop.databinding.FragmentPaymentBinding
import io.jamshid.unishop.presentation.feature_main.dialog.ErrorDialog
import io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_client_detail.pages.fragment_payment.adapter.PaymentAdapter
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class PaymentFragment : BaseFragment<FragmentPaymentBinding>(FragmentPaymentBinding::inflate) {


    private val viewModel: PaymentViewModel by viewModels()

    override fun myCreateView(savedInstanceState: Bundle?) {
        val adapter = PaymentAdapter().also { adapter ->
            binding.listPayment.adapter = adapter
        }
        if (arguments != null)
            viewModel.getAllPayments(arguments?.getLong("clientId")!!)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.allPayments.collectLatest { response ->
                when (response) {
                    is Response.Loading -> {
                        showProgress(true)
                    }
                    is Response.Error -> {
                        showProgress(false)
                        val dialog = ErrorDialog("Error")
                        dialog.show(requireActivity().supportFragmentManager, "TAG")
                        showProgress(false)
                    }
                    is Response.Success -> {
                        response.data?.let {
                            adapter.setData(it)
                        }
                        showProgress(false)
                    }
                    else -> Unit
                }
            }
        }


    }
}