package io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_client_info

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.unishop.base.BaseFragment
import io.jamshid.unishop.data.models.dto.Client
import io.jamshid.unishop.databinding.FragmentClientInfoBinding

@AndroidEntryPoint
class ClientInfoFragment :
    BaseFragment<FragmentClientInfoBinding>(FragmentClientInfoBinding::inflate) {


    private val viewModel: ClientInfoViewModel by viewModels()


    override fun myCreateView(savedInstanceState: Bundle?) {
        binding.apply {
            if (arguments!=null) {
                val client = arguments?.getSerializable("client") as Client
                clientName.text = client.fullName
                clientNumber.setText(client.phoneNumber)
                clientComment.setText(client.comment)
                navigate(client)
            }
        }
    }

    private fun navigate(client: Client) {
        binding.apply {
            outputClientContainer.setOnClickListener {
                findNavController().navigate(
                    ClientInfoFragmentDirections.actionClientInfoFragmentToClientDetailFragment(
                        client.id!!
                    )
                )
            }
            clientPayments.setOnClickListener {
                findNavController().navigate(
                    ClientInfoFragmentDirections.actionClientInfoFragmentToClientDetailFragment(
                        client.id!!
                    )
                )
            }
            imgBack.setOnClickListener {
                findNavController().navigateUp()
            }

            clientDebt.setOnClickListener {
                findNavController().navigate(
                    ClientInfoFragmentDirections.actionClientInfoFragmentToDebtFragment(
                        client
                    )
                )
            }
        }
    }


}