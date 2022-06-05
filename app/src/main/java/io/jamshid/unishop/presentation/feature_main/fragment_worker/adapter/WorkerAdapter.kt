package io.jamshid.unishop.presentation.feature_main.fragment_worker.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.jamshid.unishop.R
import io.jamshid.unishop.common.extension_functions.toSummFormat
import io.jamshid.unishop.data.models.dto.WorkerDto
import io.jamshid.unishop.databinding.ListItemWorkerBinding

// Created by Jamshid Isoqov an 6/4/2022
class WorkerAdapter : RecyclerView.Adapter<WorkerAdapter.ViewHolder>() {


    private var workerList: List<WorkerDto> = emptyList()

    inner class ViewHolder(val binding: ListItemWorkerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(workerDto: WorkerDto) {
            binding.apply {
                tvWorkerName.text = workerDto.name
                tvWorkerName.setSingleLine()
                tvWorkerName.isSelected = true
                tvWorkerPhone.text = workerDto.phone
                tvWorkerSumm.text = workerDto.toString().toSummFormat()
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemWorkerBinding.bind(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_worker, parent, false)
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = workerList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<WorkerDto>) {
        workerList = list
        notifyDataSetChanged()
    }

}