package com.memoir.home.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ctech.common.view.MaterialTextDrawable
import com.ctech.memoir.utils.OnItemClick
import com.ctech.memoir.utils.context
import com.memoir.home.databinding.ItemCurrencyBinding
import com.memoir.home.viewmodel.CurrencyItemViewModel


class CurrencyItemViewHolder(private val binding: ItemCurrencyBinding) :
    RecyclerView.ViewHolder(binding.root),
    View.OnClickListener {

    var listener: OnItemClick? = null

    private lateinit var viewModel: CurrencyItemViewModel

    companion object {
        fun create(parent: ViewGroup): CurrencyItemViewHolder {
            val binding =
                ItemCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return CurrencyItemViewHolder(binding)
        }
    }

    fun bind(viewModel: CurrencyItemViewModel) {
        this.viewModel = viewModel
        with(viewModel) {
            binding.tvSymbol.text = symbol
            binding.tvName.text = name

            MaterialTextDrawable.with(context)
                .text(firstLetter)
                .into(binding.ivIcon)
        }
    }

    override fun onClick(view: View) {
        listener?.invoke(view, viewModel)
    }
}