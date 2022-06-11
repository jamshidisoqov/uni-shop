package io.jamshid.unishop.presentation.feature_main.feature_debt.fragment_details

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.common.Response
import io.jamshid.unishop.common.extension_functions.getDateFormat
import io.jamshid.unishop.common.extension_functions.toSummFormat
import io.jamshid.unishop.data.models.dto.Output
import io.jamshid.unishop.databinding.DialogPaymentHistoryBinding
import io.jamshid.unishop.databinding.FragmentDebtDetailsBinding
import io.jamshid.unishop.presentation.feature_main.dialog.ErrorDialog
import io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_client_detail.pages.fragment_payment.adapter.PaymentAdapter
import io.jamshid.unishop.presentation.feature_main.feature_debt.fragment_details.dialog.PaymentBottomSheetDialog
import kotlinx.coroutines.flow.collectLatest
import java.util.*

@AndroidEntryPoint
class DebtDetailsFragment :
    BaseFragment<FragmentDebtDetailsBinding>(FragmentDebtDetailsBinding::inflate) {

    private val viewModel: DebtDetailsViewModel by viewModels()
    private val args: DebtDetailsFragmentArgs by navArgs()
    private lateinit var outputSales: Output
    private lateinit var paymentAdapter: PaymentAdapter
    private var choose = 0


    override fun myCreateView(savedInstanceState: Bundle?) {


        val adapter = DebtAdapter().also {
            binding.productListDebt.adapter = it
        }
        paymentAdapter = PaymentAdapter()

        if (arguments != null) {
            choose = arguments?.getInt("choose")!!
            outputSales = arguments?.getSerializable("output") as Output
            viewModel.allProducts(outputSales.id)
            viewModel.allPayments(outputSales.id)

            binding.apply {

                if (choose == 1) {
                    btnPaymentDebt.visibility = View.GONE
                }

                setData(outputSales)

                btnPaymentDebt.setOnClickListener {

                    PaymentBottomSheetDialog(
                        viewModel,
                        outputSales.id,
                        outputSales.debtAmount
                    ).also {
                        it.show(
                            requireActivity().supportFragmentManager,
                            it.tag
                        )
                    }

                }
                imgPayments.setOnClickListener {
                    showDialog()
                }
                imgBack.setOnClickListener {
                    findNavController().navigateUp()
                }

            }
        }


        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.status.collectLatest {
                if (it == 200) {
                    Snackbar.make(
                        binding.btnPaymentDebt,
                        "Successfully payed",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.allProductsByOutput.collectLatest {
                when (it) {
                    is Response.Loading -> {
                        showProgress(true)
                    }
                    is Response.Success -> {
                        showProgress(false)
                        adapter.setData(it.data!!)
                    }
                    else -> {
                        showProgress(false)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.allPaymentsByOutput.collectLatest {
                when (it) {
                    is Response.Loading -> {
                        showProgress(true)
                    }
                    is Response.Success -> {
                        showProgress(false)
                        Log.d(TAG, "myCreateView: ${it.data!!}")
                        paymentAdapter.setData(it.data!!)
                    }
                    else -> {
                        val dialog = ErrorDialog("Error")
                        dialog.show(requireActivity().supportFragmentManager,"TAG")
                        showProgress(false)
                    }
                }
            }
        }
    }

    private fun showDialog() {
        val dialog = BottomSheetDialog(requireContext())
        val binding = DialogPaymentHistoryBinding.inflate(layoutInflater, null, false)
        binding.listPaymentsForDebt.adapter = paymentAdapter
        dialog.setContentView(binding.root)
        dialog.show()

    }

    fun setData(outputSales: Output) {
        binding.apply {
            outputSales.also {
                this.tvClientName.text = it.client
                this.debtExpireDate.text = Date(it.expiredDate.time).toString().getDateFormat()
                this.lastPaymentDate.text = Date(it.updatedDate!!.time).toString().getDateFormat()
                this.tvBuyDate.text = Date(it.createdDate.time).toString().getDateFormat()
                this.tvBuySumm.text = it.amount.toLong().toString().toSummFormat()
                this.tvDebtSumm.text = it.debtAmount.toLong().toString().toSummFormat()
            }
        }
    }
}