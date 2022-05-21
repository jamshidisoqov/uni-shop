package io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_clients_list.utils

import io.jamshid.unishop.data.models.dto.Client

// Created by Jamshid Isoqov an 5/21/2022
interface OnClientClickListener {

    fun onItemClick(client: Client)

    fun onPhoneClick(client: Client)

    fun onMoneyClick(client: Client)
}