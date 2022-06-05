package io.jamshid.unishop.presentation.feature_main.feature_finance.fragment_expanses.fragment_add

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import io.jamshid.unishop.R
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.databinding.FragmentAddExpansesBinding

class AddExpansesFragment :
    BaseFragment<FragmentAddExpansesBinding>(FragmentAddExpansesBinding::inflate) {


    private val viewModel: AddExpansesViewModel by viewModels()

    override fun myCreateView(savedInstanceState: Bundle?) {

        binding.apply {
            btnAddExpanses.setOnClickListener {
                val expanses = edExpansesSalary.text.toString()
                val date = edDateExpanses.text.toString()
                val time = edTimeExpanses.text.toString()
                val comment = edCommentExpases.text.toString()
                if (expanses.isNotEmpty() && date.isNotEmpty() && time.isNotEmpty()) {

                } else {
                    Snackbar.make(
                        btnAddExpanses,
                        getString(R.string.product_sum_error),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }


}