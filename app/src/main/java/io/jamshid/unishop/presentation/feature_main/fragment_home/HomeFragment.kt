package io.jamshid.unishop.presentation.feature_main.fragment_home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.R
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.common.Constants.ROLE
import io.jamshid.unishop.databinding.FragmentHomeBinding
import io.jamshid.unishop.presentation.feature_main.feature_debt.util.Constants
import io.jamshid.unishop.utils.settings.Settings.SELLER

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewMode: HomeViewModel by viewModels()
    private val args: HomeFragmentArgs by navArgs()

    override fun myCreateView(savedInstanceState: Bundle?) {

        initRole()

        binding.apply {
            newSellContainer.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_salesFragment)
            }
            financeContainer.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_financeFragment)
            }
            clientContainer.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_clientsListFragment)
            }
            warehouseContainer.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_warehouseFragment)
            }
            debtContainer.setOnClickListener {
                Constants.choose = 1
                findNavController().navigate(R.id.action_homeFragment_to_debtFragment)
            }
        }
    }

    private fun initRole() {
        val role = args.role
        ROLE = role
        if (role == SELLER) {
            binding.workerContainer.visibility = View.GONE
        }
    }
}