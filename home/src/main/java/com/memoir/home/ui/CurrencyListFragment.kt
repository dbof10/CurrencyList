package com.memoir.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ctech.memoir.utils.OnItemClick
import com.memoir.home.databinding.FragmentCurrencyListBinding
import com.memoir.home.viewmodel.CurrencyItemViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CurrencyListFragment : Fragment() {

    private lateinit var binding: FragmentCurrencyListBinding


    private lateinit var homeAdapter: CurrencyAdapter

    companion object {
        private const val DELAY_SCROLL = 100L
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrencyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeAdapter = CurrencyAdapter(CurrencyAdapter.DIFFER)
        setupRecyclerView()
    }


    private fun setupRecyclerView() {
        val lm = LinearLayoutManager(context)
        binding.rvCurrency.adapter = homeAdapter
        binding.rvCurrency.addItemDecoration(
            DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        )
        homeAdapter.listener = OnItemClick { view, item ->

        }
        binding.rvCurrency.layoutManager = lm
    }

    fun render(list: List<CurrencyItemViewModel>, scrollToTop: Boolean) {
        homeAdapter.submitList(list)
        if (scrollToTop) {
            lifecycleScope.launch {
                delay(DELAY_SCROLL)
                binding.rvCurrency.layoutManager!!.scrollToPosition(0)
            }
        }
    }

}