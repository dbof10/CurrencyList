package com.memoir.home.viewmodel


data class HomeViewState(val content: List<CurrencyItemViewModel>? = null,
                         val search: List<CurrencyItemViewModel>? = null,
                         val loading: Boolean = false,
                         val error: Throwable? = null
)