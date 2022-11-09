package com.memoir.home.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ctech.common.view.afterTextChange
import com.ctech.common.view.onClicked
import com.memoir.home.R
import com.memoir.home.databinding.ActivityHomeBinding
import com.memoir.home.viewmodel.CurrencyItemViewModel
import com.memoir.home.viewmodel.CurrencyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DemoActivity : AppCompatActivity() {

    private lateinit var viewModel: CurrencyViewModel
    private lateinit var binding: ActivityHomeBinding

    companion object {
        private const val DEBOUNCE_SEARCH = 200L
        private const val TAG_FRAGMENT = "currency_list"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[CurrencyViewModel::class.java]

        if (savedInstanceState == null) {

            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<CurrencyListFragment>(R.id.flContent, tag = TAG_FRAGMENT)
            }
        }

        bindUi()
        setupListener()
    }

    private fun setupListener() {


        lifecycleScope.launch {
            binding.btLoad.onClicked()
                .collect {
                    viewModel.fetch()
                }
        }
        lifecycleScope.launch {
            binding.btSort.onClicked()
                .collect {
                    viewModel.sort()
                }
        }

        lifecycleScope.launch {
            binding.etSearch.afterTextChange()
                .debounce(DEBOUNCE_SEARCH)
                .collect {
                    viewModel.search(it)
                }
        }

        binding.vError.btRetry.setOnClickListener {
            viewModel.fetch()
        }
    }


    private fun bindUi() {
        viewModel.store.observe(this) {
            when {
                it.loading -> {
                    binding.vLoading.root.visibility = View.VISIBLE
                    binding.flContent.visibility = View.GONE
                    binding.vError.root.visibility = View.GONE
                }
                it.error != null -> {
                    binding.flContent.visibility = View.GONE
                    binding.vLoading.root.visibility = View.GONE
                    binding.vError.root.visibility = View.VISIBLE
                    findViewById<TextView>(R.id.tvError).text = it.error.message
                }
                it.search != null -> {
                    binding.flContent.visibility = View.VISIBLE
                    binding.vLoading.root.visibility = View.GONE
                    binding.vError.root.visibility = View.GONE
                    renderList(it.search, false)
                }
                it.content != null -> {
                    binding.flContent.visibility = View.VISIBLE
                    binding.vLoading.root.visibility = View.GONE
                    binding.vError.root.visibility = View.GONE
                    renderList(it.content, true)
                }
            }
        }
    }

    private fun renderList(list: List<CurrencyItemViewModel>, scrollToTop: Boolean) {
        val fragment = supportFragmentManager.findFragmentByTag(TAG_FRAGMENT)
        if (fragment is CurrencyListFragment) {
            fragment.render(list, scrollToTop)
        }
    }


}