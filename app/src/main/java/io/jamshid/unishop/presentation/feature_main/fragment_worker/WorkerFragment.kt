package io.jamshid.unishop.presentation.feature_main.fragment_worker

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
import io.jamshid.unishop.databinding.FragmentWorkerBinding
import io.jamshid.unishop.presentation.feature_main.fragment_worker.adapter.WorkerAdapter
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class WorkerFragment : BaseFragment<FragmentWorkerBinding>(FragmentWorkerBinding::inflate) {


    private  val viewModel: WorkerViewModel by viewModels()

    override fun myCreateView(savedInstanceState: Bundle?) {
        val adapter = WorkerAdapter()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.allWorkers.collectLatest { response ->

                when (response) {
                    is Response.Loading -> {
                        binding.pbWorker.visibility = View.VISIBLE
                        binding.root.isEnabled = false
                    }
                    is Response.Success -> {
                        binding.pbWorker.visibility = View.INVISIBLE
                        adapter.setData(response.data!!)
                        binding.root.isEnabled = true
                    }
                    else -> {
                        binding.pbWorker.visibility = View.INVISIBLE
                    }
                }
            }
        }
        binding.apply {
            listWorker.adapter = adapter
            imgBack.setOnClickListener {
                findNavController().navigateUp()
            }
            fabChooseDate.setOnClickListener {
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
    }


}