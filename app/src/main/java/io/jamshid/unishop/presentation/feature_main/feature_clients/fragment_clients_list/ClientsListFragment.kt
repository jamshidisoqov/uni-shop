package io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_clients_list

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.common.Response
import io.jamshid.unishop.common.extension_functions.dialPhone
import io.jamshid.unishop.data.models.dto.Client
import io.jamshid.unishop.databinding.FragmentClientsListBinding
import io.jamshid.unishop.presentation.MainActivity
import io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_clients_list.adapter.ClientsListAdapter
import io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_clients_list.dialog.AddClientDialog
import io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_clients_list.utils.OnClientClickListener
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ClientsListFragment :
    BaseFragment<FragmentClientsListBinding>(FragmentClientsListBinding::inflate) {

    private val viewModel: ClientsListViewModel by viewModels()

    override fun myCreateView(savedInstanceState: Bundle?) {

        val adapter = ClientsListAdapter(object : OnClientClickListener {

            override fun onItemClick(client: Client) {

            }

            override fun onPhoneClick(client: Client) {
                client.phoneNumber!!.dialPhone(requireActivity())
            }

            override fun onMoneyClick(client: Client) {
                findNavController().navigate(
                    ClientsListFragmentDirections.actionClientsListFragmentToClientDetailFragment(
                        client.id!!
                    )
                )
            }

        }).also { binding.rcvClientList.adapter = it }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.allClients.collectLatest { response ->
                when (response) {
                    is Response.Loading -> {
                        (activity as MainActivity).showProgress(true)
                    }
                    is Response.Success -> {
                        adapter.submitList(response.data!!)
                        (activity as MainActivity).showProgress(false)
                    }
                    else -> {
                        (activity as MainActivity).showProgress(false)
                    }
                }

            }
        }


        binding.imgAddClient.setOnClickListener {
            AddClientDialog(viewModel).also {
                it.show(
                    requireActivity().supportFragmentManager,
                    it.tag
                )
            }
        }
    }

}