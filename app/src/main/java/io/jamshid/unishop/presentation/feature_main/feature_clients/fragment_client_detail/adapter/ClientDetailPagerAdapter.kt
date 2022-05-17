package io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_client_detail.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_client_detail.pages.fragment_payment.PaymentFragment
import io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_client_detail.pages.fragment_sale.SaleFragment

// Created by Usmon Abdurakhmanv on 5/14/2022.

class ClientDetailPagerAdapter(
    fragment: Fragment,
    private val arguments: Bundle?
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0)
            SaleFragment.newInstance(arguments)
        else
            PaymentFragment.newInstance(arguments)
    }
}