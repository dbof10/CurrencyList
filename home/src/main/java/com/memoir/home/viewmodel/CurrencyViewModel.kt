package com.memoir.home.viewmodel

import androidx.lifecycle.ViewModel
import com.ctech.memoir.core.arch.Action
import com.ctech.memoir.core.arch.CoroutineScopeProvider
import com.ctech.memoir.core.arch.ViewStateStore
import com.ctech.memoir.core.arch.emit
import com.ctech.memoir.core.arch.produceActions
import com.memoir.home.model.CurrencyInfo
import com.memoir.home.model.SortBy
import com.memoir.home.repo.HomeRepository
import com.memoir.home.repo.SortStrategyFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.ReceiveChannel


@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val repo: HomeRepository,
    private val scopeProvider: CoroutineScopeProvider
) : ViewModel() {


    val store = ViewStateStore(HomeViewState(), scopeProvider)

    fun fetch() {
        store.dispatchActions(fetchItems())
    }

    private fun fetchItems(): ReceiveChannel<Action<HomeViewState>> {

        return produceActions(scopeProvider.async) {
            emit {
                copy(loading = true)
            }

            try {
                val properties = repo.fetch()

                val vms = properties.map {
                    toViewModel(it)
                }
                emit {
                    HomeViewState(vms, null, false, null)
                }
            } catch (e: Exception) {
                emit {
                    HomeViewState(null, null, false, e)
                }
            }
        }

    }

    fun search(keyword: String) {
        val list = store.state.content
        if (!list.isNullOrEmpty()) {
            store.dispatchActions(searchItems(keyword))
        }

    }

    private fun searchItems(keyword: String): ReceiveChannel<Action<HomeViewState>> {

        return produceActions(scopeProvider.async) {
            emit {
                copy(loading = true)
            }

            if (keyword.isEmpty()) {
                emit {
                    copy(search = null, loading = false)
                }
            } else {
                try {
                    val list = store.state.content!!
                    val foundItems = list.filter { item ->
                        item.name.contains(keyword.lowercase(), true)
                    }
                    emit {
                        copy(search = foundItems, loading = false)
                    }
                } catch (e: Exception) {
                    emit {
                        copy(error = e, loading = false)
                    }
                }
            }
        }
    }

    fun sort() {
        val list = store.state.content
        val sortBy = SortBy.Name
        if (!list.isNullOrEmpty()) {
            store.dispatchAction {
                Action {
                    val strategy = SortStrategyFactory.get(sortBy)
                    val sorted = list.sortedWith(strategy)

                    copy(content = sorted)

                }
            }
        }
    }


    private fun toViewModel(currency: CurrencyInfo): CurrencyItemViewModel {
        return CurrencyItemViewModel(
            currency.id,
            currency.name,
            currency.symbol,
            currency.symbol.first().toString()
        )
    }


}