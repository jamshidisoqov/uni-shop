package io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_client_detail.pages.fragment_payment

import android.os.Bundle
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.databinding.FragmentPaymentBinding
import io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_client_detail.pages.fragment_sale.SaleFragment

class PaymentFragment : BaseFragment<FragmentPaymentBinding>(FragmentPaymentBinding::inflate) {


    override fun myCreateView(savedInstanceState: Bundle?) {

    }

    companion object {
        fun newInstance(arguments: Bundle?) = SaleFragment().apply {
            this.arguments = arguments
        }
    }
}