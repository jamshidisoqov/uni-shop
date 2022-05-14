package io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_client_detail

import android.os.Bundle
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.databinding.FragmentClientDetailBinding

@AndroidEntryPoint
class ClientDetailFragment : BaseFragment<FragmentClientDetailBinding>(FragmentClientDetailBinding::inflate) {

    private val viewModel: ClientDetailViewModel by viewModels()

    override fun myCreateView(savedInstanceState: Bundle?) {



    }
}