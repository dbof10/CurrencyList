package com.memoir.home.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ctech.memoir.utils.OnItemClick
import com.memoir.home.viewmodel.CurrencyItemViewModel

class CurrencyAdapter(diffResult: DiffUtil.ItemCallback<CurrencyItemViewModel>) :
    ListAdapter<CurrencyItemViewModel, CurrencyItemViewHolder>(diffResult) {

    var listener: OnItemClick? = null

    companion object {

        val DIFFER = object : DiffUtil.ItemCallback<CurrencyItemViewModel>() {
            override fun areItemsTheSame(
                oldItem: CurrencyItemViewModel,
                newItem: CurrencyItemViewModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: CurrencyItemViewModel,
                newItem: CurrencyItemViewModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyItemViewHolder {
        return CurrencyItemViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CurrencyItemViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.listener = listener
    }
}