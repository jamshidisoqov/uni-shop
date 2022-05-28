package io.jamshid.unishop.presentation.feature_main.feature_finance

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.R
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.common.Response
import io.jamshid.unishop.databinding.FragmentFinanceBinding
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate


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

    @RequiresApi(Build.VERSION_CODES.O)
    val date = LocalDate.now()!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun myCreateView(savedInstanceState: Bundle?) {
        navigate()

        viewModel.getLastSevenBalance()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.lastSevenBalance.collectLatest {
                when (it) {
                    is Response.Loading -> {
                        showProgress(true)
                    }
                    is Response.Success -> {
                        showProgress(false)
                        val list = ArrayList<BarEntry>()
                        for (i in it.data!!.indices) {
                            list.add(BarEntry(i.toFloat(), it.data!![i]))
                        }
                        setBarChart(list)
                    }
                    else -> {
                        showProgress(false)
                    }
                }
            }
        }


    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun navigate() {

        binding.apply {

            containerBalance.setOnClickListener {
                findNavController().navigate(R.id.action_financeFragment_to_incomeFragment)
            }

            containerExpanses.setOnClickListener {
                findNavController().navigate(R.id.action_financeFragment_to_expansesFragment)
            }

            containerIncome.setOnClickListener {
                findNavController().navigate(R.id.action_financeFragment_to_incomeFragment)
            }

            cpb2.setProgressWithAnimation(
                (date.dayOfMonth.toFloat() / 30f * 100),
                2000,
                interpolator = null,
                100
            )
            imageView.setProgressWithAnimation((100f), 2000, interpolator = null, 0)

            tvMonthFinance.text = "${(date.month.name)}\n${date.dayOfMonth},${date.year}"

        }

    }

    private fun setBarChart(list: List<BarEntry>) {

        val barData = BarDataSet(list, "Доходы")
        barData.color = Color.parseColor("#17D837")
        barData.valueTextColor = Color.parseColor("#ffffff")
        barData.barBorderColor = Color.parseColor("#ffffff")
        barData.valueTextSize = 13f
        val bar = BarData(barData)
        binding.barchart.apply {
            setFitBars(true)
            data = bar
            setNoDataTextColor(Color.parseColor("#ffffff"))
            description.text = "Last 7 month"
            animateY(2000)
        }
    }


}