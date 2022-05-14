package io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_order

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.databinding.FragmentOrderBinding
import io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_order.adapter.OrderSpinnerAdapter
import kotlinx.coroutines.flow.collectLatest
import java.util.*

@AndroidEntryPoint
class OrderFragment : BaseFragment<FragmentOrderBinding>(FragmentOrderBinding::inflate) {

    private val viewModel: OrderViewModel by viewModels()

    override fun myCreateView(savedInstanceState: Bundle?) {

        val adapter = OrderSpinnerAdapter()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.allClients.collectLatest {
                adapter.submitList(it)
            }
        }

        binding.apply {
            imgAddClient.setOnClickListener {
//                val dialog = AddClientDialog(viewModel)
//                dialog.show(requireActivity().supportFragmentManager, dialog.tag)
            }
//            btnPayment.setOnClickListener {
//                val clientId = viewModel.allClients.value[spinnerUser.selectedItemPosition].id!!
//                val cost_price = edCash.text.toString().toDouble()
//                val cost_card = edPlastic.text.toString().toDouble()
//                val expireDate = edPaymentDate.text.toString()
//                val comment = edComment.text.toString()
//                val cash = allSumm()
//                viewModel.addSell(
//                    OutputDto(
//                        clientId,
//                        cost_price,
//                        cost_card,
//                        -1 * cash + cost_card + cost_price,
//                        Date(),
//                        comment,
//                        getList()
//                    )
//                )
//            }
        }

    }
}