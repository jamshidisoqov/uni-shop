package io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_baskets

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.R
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.common.extension_functions.toSummFormat
import io.jamshid.unishop.databinding.FragmentBasketBinding
import io.jamshid.unishop.domain.models.transfers.BasketProductModel
import io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_baskets.adapter.BasketListAdapter
import io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_baskets.util.Basket
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class BasketFragment : BaseFragment<FragmentBasketBinding>(FragmentBasketBinding::inflate) {

    private val viewModel: BasketViewModel by viewModels()

    private var lastTotalPrice = 0L

    override fun myCreateView(savedInstanceState: Bundle?) {
        val productList = Basket.products

        binding.apply {

            viewModel.setSellProducts(productList)

            val adapter = BasketListAdapter()

            binding.rcvBasket.adapter = adapter

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.saleProducts.collectLatest { baskets ->
                    adapter.submitList(baskets)
                }
            }

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.allSumm.collectLatest { all ->
                    val a = all.toLong()
                    animateTotalPrice(lastTotalPrice, a)
                    lastTotalPrice = a
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
                if (viewModel.saleProducts.value.isNotEmpty()) {
                    Basket.sellProduct =
                        viewModel.saleProducts.value as ArrayList<BasketProductModel>
                    findNavController().navigate(R.id.action_basketFragment_to_orderFragment)
                } else {
                    Snackbar.make(
                        binding.btnNextProduct,
                        getString(R.string.basket_empty),
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                }
            }

        }
    }

    @SuppressLint("SetTextI18n")
    private fun animateTotalPrice(start: Long, end: Long) {
        val animator = ValueAnimator.ofFloat(start.toFloat(), end.toFloat())
        animator.addUpdateListener {
            val newValue = (it.animatedValue as Float).toLong().toString().toSummFormat()
            binding.tvAllSum.text = getString(R.string.all) + "$newValue"
        }
        animator.duration = 500
        animator.start()
    }
}