package io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_baskets

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.R
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.databinding.FragmentBasketBinding
import io.jamshid.unishop.domain.models.transfers.BasketProductModel
import io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_baskets.adapter.BasketListAdapter
import io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_baskets.util.Basket
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class BasketFragment : BaseFragment<FragmentBasketBinding>(FragmentBasketBinding::inflate) {

    private val viewModel: BasketViewModel by viewModels()

    private val args: BasketFragmentArgs by navArgs()

    private val gson = Gson()

    override fun myCreateView(savedInstanceState: Bundle?) {

        binding.apply {
            val myType = object : TypeToken<List<BasketProductModel>>() {}.type
            val productList = gson.fromJson<List<BasketProductModel>>(args.jsonString, myType)
            viewModel.setSellProducts(productList)


            val adapter = BasketListAdapter()
            binding.rcvBasket.adapter = adapter

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.saleProducts.collectLatest { baskets ->
                    adapter.submitList(baskets)
                }
            }

            adapter.setOnAddClickListener {
                viewModel.addProduct(it)
            }

            adapter.setOnRemoveClickListener {
                viewModel.removeProduct(it)
            }


            imgBack.setOnClickListener {
                findNavController().navigateUp()
            }

            btnNextProduct.setOnClickListener {
                Basket.sellProduct = viewModel.saleProducts.value as ArrayList<BasketProductModel>
                findNavController().navigate(R.id.action_basketFragment_to_orderFragment)
            }

        }
    }
}