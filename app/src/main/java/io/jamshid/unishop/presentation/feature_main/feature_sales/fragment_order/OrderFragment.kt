package io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_order

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.R
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.data.models.dto.Client
import io.jamshid.unishop.data.models.dto.OutProductDto
import io.jamshid.unishop.data.models.dto.OutputDto
import io.jamshid.unishop.databinding.FragmentOrderBinding
import io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_baskets.util.Basket
import io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_order.adapter.OrderSpinnerAdapter
import io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_order.dialog.AddClientDialog
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

@AndroidEntryPoint
class OrderFragment : BaseFragment<FragmentOrderBinding>(FragmentOrderBinding::inflate) {

    private val viewModel: OrderViewModel by viewModels()

    private var dateInLong = System.currentTimeMillis()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun myCreateView(savedInstanceState: Bundle?) {

        val adapter = OrderSpinnerAdapter()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.allClients.collectLatest {
                adapter.submitList(it)
            }
        }


        Basket.sellProduct

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
                val costCash = edCash.text.toString().toDouble()
                val costCard = edPlastic.text.toString().toDouble()
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
                    val date1=Date(it)
                    Timber.d("tip",date1)
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
        Toast.makeText(requireContext(), "${list}", Toast.LENGTH_SHORT).show()
        return list
    }
}