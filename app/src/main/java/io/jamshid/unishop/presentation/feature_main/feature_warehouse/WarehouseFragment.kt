package io.jamshid.unishop.presentation.feature_main.feature_warehouse

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.R
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.common.Response
import io.jamshid.unishop.databinding.FragmentWarehouseBinding
import io.jamshid.unishop.domain.models.Category
import io.jamshid.unishop.domain.models.Product
import io.jamshid.unishop.presentation.feature_main.feature_warehouse.adapters.ProductAdapter
import io.jamshid.unishop.presentation.feature_main.feature_warehouse.utils.ProductItemClick
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class WarehouseFragment :
    BaseFragment<FragmentWarehouseBinding>(FragmentWarehouseBinding::inflate) {


    private val viewModel: WarehouseViewModel by viewModels()
    private var selectedCategoryId = -1
    private var selectedChipId: Int = -1


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun myCreateView(savedInstanceState: Bundle?) {


        val productAdapter = ProductAdapter(object : ProductItemClick {
            override fun onClick(product: Product) {
                val bundle = Bundle()
                bundle.putSerializable("product", product)
                findNavController().navigate(
                    R.id.action_warehouseFragment_to_productDetailsFragment,
                    bundle
                )
            }

        })

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.allProducts.collectLatest { response ->

                when (response) {
                    is Response.Loading -> {
                        binding.pbWarehouse.visibility = View.VISIBLE
                    }
                    is Response.Success -> {
                        binding.pbWarehouse.visibility = View.INVISIBLE
                        productAdapter.setData(response.data!!)
                    }
                    else -> {
                        binding.pbWarehouse.visibility = View.INVISIBLE
                    }
                }

            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.allCategories.collectLatest { response ->

                when (response) {
                    is Response.Loading -> {
                        binding.pbWarehouse.visibility = View.VISIBLE
                    }
                    is Response.Success -> {
                        binding.pbWarehouse.visibility = View.INVISIBLE
                        response.data!!.forEach { category ->
                            addNewChip(category)
                        }
                    }
                    else -> {
                        binding.pbWarehouse.visibility = View.INVISIBLE
                    }
                }

            }
        }

        binding.apply {
            rcvWareHouse.adapter = productAdapter
            imgAddProduct.setOnClickListener {
                findNavController().navigate(R.id.action_warehouseFragment_to_addProductFragment)
            }

            edSearchProduct.addTextChangedListener {
                viewModel.search(it!!.toString())
            }
        }


    }

    private fun addNewChip(category: Category) {
        try {
            binding.apply {
                val inflater = LayoutInflater.from(requireContext())
                val newChip =
                    inflater.inflate(R.layout.item_chip_choice, chipGroup, false) as Chip
                newChip.text = category.name

                chipGroup.addView(newChip)
                newChip.setOnFocusChangeListener { buttonView, isChecked ->
                    selectedCategoryId = if (isChecked) {
                        chipGroup.check((buttonView as Chip).id)
                        category.id
                    } else {
                        -1
                    }
                    if (selectedCategoryId!=-1){

                    }
                }
            }
        }catch (e:Exception){

        }
    }
}