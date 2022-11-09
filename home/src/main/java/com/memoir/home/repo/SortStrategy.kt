package com.memoir.home.repo

import com.memoir.home.viewmodel.CurrencyItemViewModel

interface SortStrategy: Comparator<CurrencyItemViewModel>

class SortByName : SortStrategy {

    override fun compare(o1: CurrencyItemViewModel, o2: CurrencyItemViewModel): Int {
        return o1.name.compareTo(o2.name)
    }
}


