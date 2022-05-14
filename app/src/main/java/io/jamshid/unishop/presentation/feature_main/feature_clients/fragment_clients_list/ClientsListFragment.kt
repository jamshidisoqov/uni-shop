package io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_clients_list

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.databinding.FragmentClientsListBinding
import io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_clients_list.adapter.ClientsListAdapter
import io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_clients_list.dialog.AddClientDialog
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ClientsListFragment : BaseFragment<FragmentClientsListBinding>(FragmentClientsListBinding::inflate) {

    private val viewModel: ClientsListViewModel by viewModels()

    override fun myCreateView(savedInstanceState: Bundle?) {

        val adapter = ClientsListAdapter().also { binding.rcvClientList.adapter = it }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.allClients.collectLatest { clients ->
                adapter.submitList(clients)
            }
        }

        adapter.setOnItemClickListener {
            Toast.makeText(requireContext(), "Call icon has Clicked", Toast.LENGTH_SHORT).show()
        }

        adapter.setOnItemPhoneClickListener {
            Toast.makeText(requireContext(), "Call icon has Clicked", Toast.LENGTH_SHORT).show()
        }

        adapter.setOnItemMoneyClickListener {

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