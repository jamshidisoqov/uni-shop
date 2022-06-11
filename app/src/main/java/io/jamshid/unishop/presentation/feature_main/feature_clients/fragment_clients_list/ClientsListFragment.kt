package io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_clients_list

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.R
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.common.Response
import io.jamshid.unishop.common.extension_functions.dialPhone
import io.jamshid.unishop.data.models.dto.Client
import io.jamshid.unishop.databinding.FragmentClientsListBinding
import io.jamshid.unishop.presentation.feature_main.dialog.ErrorDialog
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
                val bundle = Bundle()
                bundle.putSerializable("client", client.toClient())
                //Toast.makeText(requireContext(), "${client.toClient()}", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.clientInfoFragment, bundle)
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
                        binding.pbClient.visibility = View.VISIBLE
                    }
                    is Response.Success -> {
                        adapter.submitList(response.data!!)
                        binding.pbClient.visibility = View.INVISIBLE
                    }
                    else -> {
                        showProgress(false)
                        val dialog = ErrorDialog("Error")
                        dialog.show(requireActivity().supportFragmentManager,"TAG")
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

        binding.edSearchClient.addTextChangedListener {
            viewModel.searchClient(it!!.toString())
        }


    }

}