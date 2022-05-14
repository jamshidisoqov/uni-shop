package io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_clients_list

import android.os.Bundle
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.databinding.FragmentClientsListBinding

@AndroidEntryPoint
class ClientsListFragment : BaseFragment<FragmentClientsListBinding>(FragmentClientsListBinding::inflate) {

    private val viewModel: ClientsListViewModel by viewModels()

    override fun myCreateView(savedInstanceState: Bundle?) {


    }

}