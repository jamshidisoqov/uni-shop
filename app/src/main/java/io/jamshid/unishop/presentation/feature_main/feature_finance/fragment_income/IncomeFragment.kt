package io.jamshid.unishop.presentation.feature_main.feature_finance.fragment_income

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.R
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.common.Response
import io.jamshid.unishop.data.models.dto.OutputSales
import io.jamshid.unishop.databinding.FragmentIncomeBinding
import io.jamshid.unishop.presentation.MainActivity
import io.jamshid.unishop.presentation.feature_main.feature_finance.fragment_income.adapter.IncomeAdapter
import io.jamshid.unishop.utils.OnItemClickListener
import kotlinx.coroutines.flow.collectLatest
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class IncomeFragment : BaseFragment<FragmentIncomeBinding>(FragmentIncomeBinding::inflate) {

    private val viewModel: IncomeViewModel by viewModels()
    private var dateFromInLong = System.currentTimeMillis()
    private var dateFrom = SimpleDateFormat("dd.MM.yyyy", Locale.ROOT).format(dateFromInLong)
    private var dateToInLong = System.currentTimeMillis()
    private var dateTo = SimpleDateFormat("dd.MM.yyyy", Locale.ROOT).format(dateToInLong)
    private var lastSum = 0.0

    override fun myCreateView(savedInstanceState: Bundle?) {


        val adapter = IncomeAdapter(object : OnItemClickListener<OutputSales> {
            override fun onClick(data: OutputSales) {
                val bundle = Bundle()
                bundle.putSerializable("output", data.toOutput())
                bundle.putInt("choose", 1)
                findNavController().navigate(R.id.debtDetailsFragment, bundle)
            }
        })

        viewModel.getAllSales()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.allSales.collectLatest {
                when (it) {
                    is Response.Loading -> {
                        (activity as MainActivity).showProgress(true)
                    }
                    is Response.Success -> {
                        (activity as MainActivity).showProgress(false)
                        adapter.setData(it.data!!)
                    }
                    else -> {
                        (activity as MainActivity).showProgress(false)
                    }
                }
            }
        }

        binding.apply {

            rcvSalesAll.adapter = adapter

            edIncomeStart.setOnClickListener {
                val datePickerDialog = MaterialDatePicker.Builder.datePicker()
                    .setTitleText(context?.getString(R.string.start_date))
                    .setSelection(dateFromInLong)
                    .setCalendarConstraints(
                        CalendarConstraints.Builder()
                            .setEnd(System.currentTimeMillis())
                            .setValidator(DateValidatorPointBackward.now())
                            .build()
                    )
                    .build()

                datePickerDialog.addOnPositiveButtonClickListener {
                    dateFromInLong = it
                    dateFrom = SimpleDateFormat("dd.MM.yyyy", Locale.ROOT).format(dateFromInLong)
                    edIncomeStart.setText(dateFrom)
                }

                datePickerDialog.show(requireActivity().supportFragmentManager, "DatePicker")
            }

            edIncomeEnd.setOnClickListener {
                val datePickerDialog = MaterialDatePicker.Builder.datePicker()
                    .setTitleText(context?.getString(R.string.end_date))
                    .setSelection(dateToInLong)
                    .setCalendarConstraints(
                        CalendarConstraints.Builder()
                            .setEnd(System.currentTimeMillis())
                            .setValidator(DateValidatorPointBackward.now())
                            .build()
                    )
                    .build()

                datePickerDialog.addOnPositiveButtonClickListener {
                    dateToInLong = it
                    dateTo = SimpleDateFormat("dd.MM.yyyy", Locale.ROOT).format(dateToInLong)
                    edIncomeEnd.setText(dateTo)
                }

                datePickerDialog.show(requireActivity().supportFragmentManager, "DatePicker")
            }

            btnCalculate.setOnClickListener {
                viewModel.sortedFromDate(dateFromInLong, dateToInLong)
            }

        }
        navigate()
    }

    private fun navigate() {

        binding.apply {
            imgBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }
}