package io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_order.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import io.jamshid.unishop.R
import io.jamshid.unishop.data.models.dto.ClientDto
import io.jamshid.unishop.databinding.FragmentDialogAddClientBinding
import io.jamshid.unishop.presentation.feature_main.feature_sales.fragment_order.OrderViewModel
import io.jamshid.unishop.utils.MaskWatcher

// Created by Usmon Abdurakhmanv on 5/14/2022.

class AddClientDialog(
    private var viewModel: OrderViewModel
) : DialogFragment() {


    private var binding: FragmentDialogAddClientBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentDialogAddClientBinding.inflate(inflater, container, false)


        dialog!!.window?.setBackgroundDrawableResource(R.drawable.dialog_bg)

        binding!!.apply {
            edClientPhoneNumber.addTextChangedListener(MaskWatcher.phoneNumber())
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