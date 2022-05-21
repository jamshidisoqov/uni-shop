package io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_client_detail

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.R
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.databinding.FragmentClientDetailBinding
import io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_client_detail.adapter.ClientDetailPagerAdapter

@AndroidEntryPoint
class ClientDetailFragment : BaseFragment<FragmentClientDetailBinding>(FragmentClientDetailBinding::inflate) {

    private val viewModel: ClientDetailViewModel by viewModels()
    private val args:ClientDetailFragmentArgs by navArgs()

    override fun myCreateView(savedInstanceState: Bundle?) {

        binding.vp2Client.adapter = ClientDetailPagerAdapter(args.clientId,this)

        TabLayoutMediator(
            binding.tabLayoutClient,
            binding.vp2Client
        ) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()
    }

    private fun getTabTitle(position: Int): String {
        return when (position) {
            0 -> getString(R.string.sales)
            1 -> getString(R.string.payment)
            else -> throw IndexOutOfBoundsException("Pager position couldn't be $position. Cause we have a only 2 pages")
        }
    }
}