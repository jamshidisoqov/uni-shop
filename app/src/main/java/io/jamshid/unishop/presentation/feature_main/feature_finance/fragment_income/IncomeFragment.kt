package io.jamshid.unishop.presentation.feature_main.feature_finance.fragment_income

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.util.Pair
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.R
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.common.Response
import io.jamshid.unishop.common.extension_functions.toSummFormat
import io.jamshid.unishop.data.models.dto.OutputSales
import io.jamshid.unishop.databinding.FragmentIncomeBinding
import io.jamshid.unishop.presentation.feature_main.feature_finance.fragment_income.adapter.IncomeAdapter
import io.jamshid.unishop.utils.OnItemClickListener
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class IncomeFragment : BaseFragment<FragmentIncomeBinding>(FragmentIncomeBinding::inflate) {

    private val viewModel: IncomeViewModel by viewModels()
    private var lastValue = 0L

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
                        binding.pbIncome.visibility = View.VISIBLE
                    }
                    is Response.Success -> {
                        binding.pbIncome.visibility = View.INVISIBLE
                        adapter.setData(it.data!!)
                        calculateSumm(it.data!!)
                    }
                    else -> {
                        binding.pbIncome.visibility = View.INVISIBLE
                    }
                }
            }
        }

        binding.apply {

            rcvSalesAll.adapter = adapter

            fabCalendar.setOnClickListener {
                val date = MaterialDatePicker.Builder.dateRangePicker().setSelection(
                    Pair.create(
                        MaterialDatePicker.thisMonthInUtcMilliseconds(),
                        MaterialDatePicker.todayInUtcMilliseconds()
                    )
                ).build()
                date.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomCalendarView)
                date.show(requireActivity().supportFragmentManager, date.tag)
                date.addOnPositiveButtonClickListener {
                    viewModel.sortedFromDate(it.first!!, it.second!!)
                }
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

    @SuppressLint("SetTextI18n")
    private fun calculateSumm(list: List<OutputSales>) {

        var allSumm = 0.0
        var debt = 0.0

        for (i in list) {
            allSumm += (i.amount)
            debt += (i.debtAmount)
        }

        animateTotalPrice(lastValue,allSumm.toLong())
        lastValue = allSumm.toLong()
        binding.tvDebtSumm.text = getString(R.string.payment_debt)+"::"+"${debt.toLong()}".toSummFormat()



    }

    @SuppressLint("SetTextI18n")
    private fun animateTotalPrice(start: Long, end: Long) {
        val animator = ValueAnimator.ofFloat(start.toFloat(), end.toFloat())
        animator.addUpdateListener {
            val newValue = (it.animatedValue as Float).toLong().toString().toSummFormat()
            binding.tvAllSum.text = getString(R.string.all)+":$newValue"
        }
        animator.duration = 500
        animator.start()
    }

}