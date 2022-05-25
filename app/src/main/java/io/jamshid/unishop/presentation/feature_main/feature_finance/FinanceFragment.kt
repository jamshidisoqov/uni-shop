package io.jamshid.unishop.presentation.feature_main.feature_finance

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.R
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.databinding.FragmentFinanceBinding
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

        val list = ArrayList<BarEntry>()
        list.add(BarEntry(1f, 1200f))
        list.add(BarEntry(2f, 200f))
        list.add(BarEntry(3f, 2300f))
        list.add(BarEntry(4f, 1234f))
        list.add(BarEntry(5f, 734f))
        list.add(BarEntry(6f, 1275f))
        list.add(BarEntry(7f, 1892f))

        val barData = BarDataSet(list, "Доходы")
        barData.color = Color.parseColor("#17D837")
        barData.valueTextColor = Color.parseColor("#ffffff")
        barData.barBorderColor=Color.parseColor("#ffffff")
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

            cpb2.setProgressWithAnimation((date.dayOfMonth.toFloat() / 30f * 100),2000,interpolator = null,0)

            tvMonthFinance.text = "${(date.month.name)}\n${date.dayOfMonth},${date.year}"

        }

    }


}