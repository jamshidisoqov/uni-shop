package io.jamshid.unishop.presentation.feature_main.feature_debt.fragment_details.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.jamshid.unishop.R
import io.jamshid.unishop.common.extension_functions.getOnlyDigits
import io.jamshid.unishop.data.models.OutputPayment
import io.jamshid.unishop.databinding.DilogPaymentBottomSheetBinding
import io.jamshid.unishop.presentation.feature_main.feature_debt.fragment_details.DebtDetailsViewModel
import io.jamshid.unishop.utils.MaskWatcherPayment

// Created by Jamshid Isoqov an 5/23/2022
class PaymentBottomSheetDialog(private var vm: DebtDetailsViewModel,private var id:Long) : BottomSheetDialogFragment() {

    private lateinit var binding:DilogPaymentBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.dialog_bg)
        binding = DilogPaymentBottomSheetBinding.inflate(inflater,container,false)

        binding.edCardDebt.addTextChangedListener(MaskWatcherPayment(binding.edCardDebt))
        binding.edCashDebt.addTextChangedListener(MaskWatcherPayment(binding.edCashDebt))



        binding.btnAddPaymentForDebt.setOnClickListener {
            val cash  =  binding.edCashDebt.text.toString().getOnlyDigits()
            val card  =  binding.edCardDebt.text.toString().getOnlyDigits()
            if (cash.isNotEmpty()||card.isNotEmpty()){
                val payment = OutputPayment(id,card.toDouble(),card.toDouble())
                vm.paymentForOutput(payment)
                dialog!!.dismiss()
            }else{
                binding.edCardDebt.error = getString(R.string.product_sum_error)
                binding.edCashDebt.error = getString(R.string.product_sum_error)
            }
        }



        return binding.root
    }
}