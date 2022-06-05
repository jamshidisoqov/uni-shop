package io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_order

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.R
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.common.extension_functions.getOnlyDigits
import io.jamshid.unishop.common.extension_functions.toSummFormat
import io.jamshid.unishop.data.models.dto.Client
import io.jamshid.unishop.data.models.dto.OutProductDto
import io.jamshid.unishop.data.models.dto.OutputDto
import io.jamshid.unishop.databinding.FragmentOrderBinding
import io.jamshid.unishop.presentation.MainActivity
import io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_baskets.util.Basket
import io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_order.adapter.OrderSpinnerAdapter
import io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_order.dialog.AddClientDialog
import io.jamshid.unishop.utils.MaskWatcherPayment
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class OrderFragment : BaseFragment<FragmentOrderBinding>(FragmentOrderBinding::inflate) {

    private val viewModel: OrderViewModel by viewModels()

    private var dateInLong = System.currentTimeMillis()

    private var allSumm = 0.0

    private var isCash = true


    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun myCreateView(savedInstanceState: Bundle?) {

        val adapter = OrderSpinnerAdapter()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.allClients.collectLatest {
                adapter.submitList(it)
            }
        }

        allSumm = allSumma()

        binding.apply {
            imgAddClient.setOnClickListener {
                AddClientDialog(viewModel).also {
                    it.show(
                        requireActivity().supportFragmentManager,
                        it.tag
                    )
                }
            }

            btnPayment.setOnClickListener {
                val clientId = (spinnerUser.selectedItem as Client).id
                val costCash = edCash.text.toString().getOnlyDigits().toDouble()
                val costCard = edPlastic.text.toString().getOnlyDigits().toDouble()
                val comment = edComment.text.toString()
                val cash = allSumma()
                viewModel.addSell(
                    OutputDto(
                        clientId = clientId!!,
                        costCash = costCash,
                        costCard = costCard,
                        costDebt = -1 * cash + costCard + costCash,
                        expiredDate = dateInLong,
                        comment = comment,
                        products = getList()
                    )
                )

            }

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.addSalesStatus.collectLatest {
                    if (it == 200) {
                        (activity as MainActivity).setNewLocale()
                    }
                }
            }

            binding.edCash.addTextChangedListener(MaskWatcherPayment(binding.edCash))
            binding.edPlastic.addTextChangedListener(MaskWatcherPayment(binding.edPlastic))

            binding.edCash.addTextChangedListener {
                isCash = true
                calculateDebt()
            }

            binding.edPlastic.addTextChangedListener {
                isCash = false
                calculateDebt()
            }

            binding.edPaymentDate.setOnClickListener {
                val datePickerDialog = MaterialDatePicker.Builder.datePicker()
                    .setTitleText(context?.getString(R.string.choose_date_uz))
                    .setSelection(dateInLong)
                    .setCalendarConstraints(
                        CalendarConstraints.Builder()
                            .setStart(System.currentTimeMillis())
                            .setValidator(DateValidatorPointForward.now())
                            .build()
                    )
                    .build()

                datePickerDialog.addOnPositiveButtonClickListener {
                    dateInLong = it
                    val date1 = Date(it)
                    val date = SimpleDateFormat("dd.MM.yyyy", Locale.ROOT).format(dateInLong)
                    binding.edPaymentDate.setText(date)
                }

                datePickerDialog.addOnDismissListener {

                }

                datePickerDialog.show(
                    requireActivity().supportFragmentManager,
                    datePickerDialog.tag
                )
            }
            binding.spinnerUser.adapter = adapter
            binding.tvProductAllSumm.text = getString(R.string.all) +"${allSumm.toLong()}".toSummFormat()
            binding.tvProductDebtSumm.text = getString(R.string.payment_debt)+":"+"${allSumm.toLong()}".toSummFormat()
        }

    }

    private fun allSumma(): Double {
        var s = 0.0
        Basket.sellProduct.forEach {
            s += it.quantity * it.cost
        }
        return s
    }

    private fun getList(): List<OutProductDto> {
        val list = ArrayList<OutProductDto>()
        Basket.sellProduct.forEach {
            list.add(it.toOutputDto())
        }
        return list
    }

    @SuppressLint("SetTextI18n")
    private fun calculateDebt() {

        binding.apply {
            val cashString  = edCash.text.toString()
            val cardString  = edPlastic.text.toString()
            val cash = if (cashString.isNotEmpty()) cashString.getOnlyDigits().toDouble() else 0.0
            val card = if (cardString.isNotEmpty()) cardString.getOnlyDigits().toDouble() else 0.0
            val debt = allSumm - cash - card
            if (debt < 0) {
                if (isCash) {
                    edCash.error = "Сумма превысила указанную сумму"
                } else edPlastic.error = "Сумма превысила указанную сумму"
            }
            tvProductDebtSumm.text = "Долг:" + "${debt.toLong()}".toSummFormat()
        }


    }
}