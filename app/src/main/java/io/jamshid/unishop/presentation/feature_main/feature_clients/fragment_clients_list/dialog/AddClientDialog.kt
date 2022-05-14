package io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_clients_list.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import io.jamshid.unishop.data.models.dto.ClientDto
import io.jamshid.unishop.databinding.FragmentDialogAddClientBinding
import io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_clients_list.ClientsListViewModel

// Created by Usmon Abdurakhmanv on 5/14/2022.

class AddClientDialog(
    private var viewModel: ClientsListViewModel
) : DialogFragment() {


    private var binding: FragmentDialogAddClientBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentDialogAddClientBinding.inflate(inflater, container, false)

        binding!!.apply {
            btnAddClient.setOnClickListener {
                val name = edClientName.text.toString()
                val phone = edClientPhoneNumber.text.toString()
                val comment = edClientComment.text.toString()

                if (name.isNotEmpty() && phone.isNotEmpty() && comment.isNotEmpty()) {
                    viewModel.addClient(
                        ClientDto(
                            fullName = name,
                            phoneNumber = phone,
                            comment = comment
                        )
                    )
                    dialog!!.dismiss()
                }
            }
        }
        return binding!!.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}