package io.jamshid.unishop.presentation.feature_main.feature_warehouse.fragment_product_details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.jamshid.unishop.R

class ProductDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = ProductDetailsFragment()
    }

    private lateinit var viewModel: ProductDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}