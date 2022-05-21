package io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_client_detail.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_client_detail.pages.fragment_payment.PaymentFragment
import io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_client_detail.pages.fragment_sale.SaleFragment

// Created by Usmon Abdurakhmanv on 5/14/2022.

class ClientDetailPagerAdapter(private val id: Long, fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putLong("clientId", id)
        return if (position == 0) {
            val fragment = SaleFragment()
            fragment.arguments = bundle
            fragment
        } else {
            val fragment = PaymentFragment()
            fragment.arguments = bundle;
            fragment

        }
    }
}