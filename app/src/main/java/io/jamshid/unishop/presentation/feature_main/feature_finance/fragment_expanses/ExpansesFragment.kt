package io.jamshid.unishop.presentation.feature_main.feature_finance.fragment_expanses

import android.os.Bundle
import android.view.View
import androidx.core.util.Pair
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.R
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.common.Response
import io.jamshid.unishop.data.models.dto.ExpansesDto
import io.jamshid.unishop.databinding.DialogExpansesDetailsBinding
import io.jamshid.unishop.databinding.FragmentExpansesBinding
import io.jamshid.unishop.presentation.feature_main.feature_finance.fragment_expanses.adapter.ExpansesAdapter
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ExpansesFragment : BaseFragment<FragmentExpansesBinding>(FragmentExpansesBinding::inflate) {


    private val viewModel: ExpansesViewModel by viewModels()

    override fun myCreateView(savedInstanceState: Bundle?) {

        val adapter = ExpansesAdapter()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.allExpanses.collectLatest { response ->

                when (response) {
                    is Response.Loading -> {
                        binding.pbExpanses.visibility = View.VISIBLE
                    }
                    is Response.Success -> {
                        binding.pbExpanses.visibility = View.INVISIBLE
                        adapter.setData(response.data!!)
                    }
                    else -> {
                        binding.pbExpanses.visibility = View.INVISIBLE
                    }
                }

            }
        }

        adapter.setOnItemClickListener { expansesDto ->

            showDialog(expansesDto)

        }
        binding.apply {
            rcvExpanses.adapter = adapter
            fabDateCalendar.setOnClickListener {
                val date = MaterialDatePicker.Builder.dateRangePicker().setSelection(
                    Pair.create(
                        MaterialDatePicker.thisMonthInUtcMilliseconds(),
                        MaterialDatePicker.todayInUtcMilliseconds()
                    )
                ).build()
                date.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomCalendarView)
                date.show(requireActivity().supportFragmentManager, date.tag)
                date.addOnPositiveButtonClickListener {
                    viewModel.getExpansesByDate(it.first!!, it.second!!)
                }
            }
            imgBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }

    }

    private fun showDialog(expansesDto: ExpansesDto) {
        val dialog = BottomSheetDialog(requireContext())
        val dbBinding = DialogExpansesDetailsBinding.inflate(layoutInflater)
        dbBinding.apply {

        }
        dialog.setContentView(dbBinding.root)
    }


}