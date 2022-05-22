package io.jamshid.unishop.presentation.feature_main.feature_debt.fragment_details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.jamshid.unishop.R

class DebtDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = DebtDetailsFragment()
    }

    private lateinit var viewModel: DebtDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_debt_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DebtDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}