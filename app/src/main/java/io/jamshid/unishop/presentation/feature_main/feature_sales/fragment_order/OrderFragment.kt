package io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_order

import android.os.Build
import android.os.Bundle
import android.widget.Toast
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
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class OrderFragment : BaseFragment<FragmentOrderBinding>(FragmentOrderBinding::inflate) {

    private val viewModel: OrderViewModel by viewModels()

    private var dateInLong = System.currentTimeMillis()

    private var allSumm = 0.0


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
                    Toast.makeText(requireContext(), "$it", Toast.LENGTH_SHORT).show()
                    if (it == 200) {
                        (activity as MainActivity).setNewLocale()
                    }
                }
            }

            binding.edCash.addTextChangedListener(MaskWatcherPayment(binding.edCash))
            binding.edPlastic.addTextChangedListener(MaskWatcherPayment(binding.edPlastic))

            binding.edCash.addTextChangedListener {
                if (it!!.toString().isNotEmpty()) {
                    val min = allSumm - it.toString().getOnlyDigits().toDouble()
                    if (binding.edPlastic.text.toString().isNotEmpty())
                        min - binding.edPlastic.text.toString().getOnlyDigits().toDouble()
                    tvProductDebtSumm.text = "$min"
                }
            }

            binding.edPlastic.addTextChangedListener {
                if (it!!.toString().isNotEmpty()) {
                    val min = allSumm - it.toString().getOnlyDigits()
                        .toDouble()
                    if (edCash.text.toString().isNotEmpty())
                        min - edCash.text.toString().getOnlyDigits().toDouble()
                    tvProductDebtSumm.text = "$min"
                }
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
                    Timber.d("tip", date1)
                    Timber.d(date1.toString())

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
        }

        binding.tvProductAllSumm.text = "$allSumm"
        binding.tvProductDebtSumm.text = binding.tvProductAllSumm.text
        binding.spinnerUser.adapter = adapter

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
}