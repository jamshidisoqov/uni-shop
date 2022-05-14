package io.jamshid.unishop.presentation.feature_main.feature_clients.fragment_clients_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.jamshid.unishop.databinding.ListItemClientBinding
import io.jamshid.unishop.domain.models.Client

// Created by Usmon Abdurakhmanv on 5/14/2022.

class ClientsListAdapter : RecyclerView.Adapter<ClientsListAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ListItemClientBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBindView(client: Client) {
            binding.apply {
                tvClientName.text = client.fullName

                root.setOnClickListener {
                    onItemClick?.invoke(client)
                }

                imgPhone.setOnClickListener {
                    onItemPhoneClick?.invoke(client)
                }

                imgMoney.setOnClickListener {
                    onItemMoneyClick?.invoke(client)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemClientBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.onBindView(differ.currentList[position])

    override fun getItemCount(): Int = differ.currentList.size


    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Client>() {
        override fun areItemsTheSame(oldItem: Client, newItem: Client): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Client, newItem: Client): Boolean {
            return oldItem == newItem
        }
    })

    fun submitList(list: List<Client>) {
        differ.submitList(list)
    }

    //Click listeners

    private var onItemClick: ((Client) -> Unit)? = null

    fun setOnItemClickListener(onClick: (Client) -> Unit) {
        onItemClick = onClick
    }

    private var onItemPhoneClick: ((Client) -> Unit)? = null

    fun setOnItemPhoneClickListener(onClick: (Client) -> Unit) {
        onItemClick = onClick
    }

    private var onItemMoneyClick: ((Client) -> Unit)? = null

    fun setOnItemMoneyClickListener(onClick: (Client) -> Unit) {
        onItemClick = onClick
    }
}